package it.ingsoft.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class TestTurno {
	private Utente utenteRossi;
	private Struttura strMis;
	private Tempo tempo;
	private Turno turno;
	
	@Before
	public void init() {
		this.strMis = new Struttura();
		this.strMis.setNomeStruttura("misano");
		this.strMis.setFotoFatturaValida(new File("configDB"));
		this.strMis.setPartitaIva("1234567890");
		this.strMis.setIban("MMMMMMM");
		this.strMis.setNazione("Italia");
		this.strMis.setProvincia("Pesaro Urbino");
		this.strMis.setCitta("Pesaro");
		this.strMis.setVia("via Tavullia");
		this.strMis.setNumeroCivico("46");
		this.strMis.setCap("46464".toCharArray());

		this.utenteRossi = new Utente();
		this.utenteRossi.setNome("Valentino");
		this.utenteRossi.setCognome("Rossi");
		this.utenteRossi.setCodiceFiscale("RSSVNT79B16G479B");
		this.utenteRossi.setDataDiNascita(LocalDate.of(1979, 2, 16));
		this.utenteRossi.setLuogoDiNascita("Pesaro");
		this.utenteRossi.setNumeroDiTelefono("4646464646");
		this.utenteRossi.setNazione("Italia");
		this.utenteRossi.setProvincia("Pesaro Urbino");
		this.utenteRossi.setCitta("Pesaro");
		this.utenteRossi.setVia("via Tavullia");
		this.utenteRossi.setNumeroCivico("46");
		this.utenteRossi.setCap("46464".toCharArray());

		this.tempo = new Tempo();
		this.tempo.setIdTempo(1);
		this.tempo.setUtente(utenteRossi);
		this.tempo.setValore(1000);
		
		this.turno = new Turno();
		this.turno.setId(1);
		this.turno.setStruttura(this.strMis);
		this.turno.setInizio(LocalDateTime.of(2021, 2, 22, 0, 0));
		this.turno.setFine(LocalDateTime.of(2021, 2, 22, 3, 0));
		this.turno.setPostiDisponibili(10);
		this.turno.setPrezzo(59.99F);
		this.turno.getTempi().add(this.tempo);
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(1, this.turno.getId());
		assertSame(this.strMis, this.turno.getStruttura());
		assertEquals(LocalDateTime.of(2021, 2, 22, 0, 0), this.turno.getInizio());
		assertEquals(LocalDateTime.of(2021, 2, 22, 3, 0), this.turno.getFine());
		assertEquals(10, this.turno.getPostiDisponibili());
		assertEquals(59.99F, this.turno.getPrezzo());
		assertEquals(1, this.turno.getTempi().size());
		assertSame(this.tempo, this.turno.getTempi().get(0));
		assertEquals(this.tempo, this.tempo);
	}
	
	@Test
	public void testDB()
	{
		String flagMsg = null;
		
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			UtenteDAO utenteDAO = factory.getUtenteDAO();
			StrutturaDAO strutturaDAO = factory.getStrutturaDAO();
			TempoDAO tempoDAO = factory.getTempoDAO();
			
			try {
				
				TurnoDAO turnoDAO = factory.getTurnoDAO();
				
				utenteDAO.createTable();
				utenteDAO.insert(this.utenteRossi);
				strutturaDAO.createTable();
				strutturaDAO.insert(this.strMis);
				
				turnoDAO.createTable();
				tempoDAO.createTable();
				
				turnoDAO.insert(this.turno);
				Turno temp = turnoDAO.get(this.turno.getId());
				Tempo tempT = tempoDAO.get(this.tempo.getIdTempo());
				temp.setTempi(tempoDAO.getByTurno(this.turno.getId()));
				
				assertEquals(this.turno,temp);
				assertEquals(this.tempo,tempT);
				
				tempoDAO.update(this.tempo);
				temp = turnoDAO.get(this.turno.getId());
				temp.setTempi(tempoDAO.getByTurno(turno.getId()));
				
				assertEquals(this.turno,temp);
				
				tempoDAO.delete(this.tempo);
				turnoDAO.delete(this.turno);
				
				assertThrows(SQLException.class, () -> tempoDAO.get(this.tempo.getIdTempo()));
				assertThrows(SQLException.class, () -> tempoDAO.get(this.turno.getId()));
				
				tempoDAO.dropTable();
				turnoDAO.dropTable();
				
				assertThrows(SQLException.class, () -> turnoDAO.dropTable());
				
			} catch (SQLException e) {
				e.printStackTrace();
				flagMsg = e.getMessage();
				
			} finally {
				utenteDAO.dropTable();
				strutturaDAO.dropTable();
			}
		} catch(SQLException e) {
			
		}
		
		if(flagMsg != null) fail(flagMsg);
	}
}
