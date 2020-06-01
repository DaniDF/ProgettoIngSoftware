package it.ingsoft.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class TestTempi {
	private Utente utenteRossi;
	private Tempo tempo;
	
	@Before
	public void init() {
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
	}
	
	@Test
	public void testEquals()
	{
		assertEquals(1, this.tempo.getIdTempo());
		assertSame(this.utenteRossi, this.tempo.getUtente());
		assertEquals(1000, this.tempo.getValore());
	}
	
	@Test
	public void testDB()
	{
		String flagMsg = null;
		
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			UtenteDAO utenteDAO = factory.getUtenteDAO();
			try {
				
				TempoDAO tempoDAO = factory.getTempoDAO();
				
				utenteDAO.createTable();
				utenteDAO.insert(this.utenteRossi);
				
				tempoDAO.createTable();
				//tempoDAO.insert(this.tempo);
				//Tempo temp = tempoDAO.get(this.tempo.getIdTempo());
				
				//assertEquals(this.tempo,temp);
				
				//tempoDAO.update(tempo);
				//temp = tempoDAO.get(this.tempo.getIdTempo());
				
				//assertEquals(this.tempo,temp);
				
				//tempoDAO.delete(tempo);
				
				//assertThrows(SQLException.class, () -> tempoDAO.get(this.tempo.getIdTempo()));
				
				tempoDAO.dropTable();
				
				assertThrows(SQLException.class, () -> tempoDAO.dropTable());
				
			} catch (SQLException e) {
				e.printStackTrace();
				flagMsg = e.getMessage();
				
			} finally {
				utenteDAO.dropTable();
			}
		} catch(SQLException e) {
			
		}
		
		if(flagMsg != null) fail(flagMsg);
	}
}
