package it.ingsoft.test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.metodoPagamento.Carta;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class TestMainFattura {
	
	private static Utente utenteRossi;
	private static Struttura strMis;
	private static Turno turno;
	private static Fattura fattura;
	
	private static void init() {
		strMis = new Struttura();
		strMis.setNomeStruttura("misano");
		strMis.setFotoFatturaValida(new File("configDB"));
		strMis.setPartitaIva("1234567890");
		strMis.setIban("MMMMMMM");
		strMis.setNazione("Italia");
		strMis.setProvincia("Pesaro Urbino");
		strMis.setCitta("Pesaro");
		strMis.setVia("via Tavullia");
		strMis.setNumeroCivico("46");
		strMis.setCap("46464".toCharArray());

		utenteRossi = new Utente();
		utenteRossi.setNome("Valentino");
		utenteRossi.setCognome("Rossi");
		utenteRossi.setCodiceFiscale("RSSVNT79B16G479B");
		utenteRossi.setDataDiNascita(LocalDate.of(1979, 2, 16));
		utenteRossi.setLuogoDiNascita("Pesaro");
		utenteRossi.setNumeroDiTelefono("4646464646");
		utenteRossi.setNazione("Italia");
		utenteRossi.setProvincia("Pesaro Urbino");
		utenteRossi.setCitta("Pesaro");
		utenteRossi.setVia("via Tavullia");
		utenteRossi.setNumeroCivico("46");
		utenteRossi.setCap("46464".toCharArray());
		
		turno = new Turno();
		turno.setId(1);
		turno.setStruttura(strMis);
		turno.setInizio(LocalDateTime.of(2021, 2, 22, 0, 0));
		turno.setFine(LocalDateTime.of(2021, 2, 22, 3, 0));
		turno.setPostiDisponibili(10);
		turno.setPrezzo(59.99F);
		
		fattura = new Fattura();
		fattura.setIdFattura(1);
		fattura.setMetodoPagamento(new Carta());
		fattura.setUtente(utenteRossi);
		fattura.setStruttura(strMis);
		fattura.getAcquisti().add(turno);
	}
	
	public static void main(String[] args)
	{
		init();
		
		String flagMsg = null;
		
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.MYSQL);
		
		try
		{
			UtenteDAO utenteDAO = factory.getUtenteDAO();
			StrutturaDAO strutturaDAO = factory.getStrutturaDAO();
			TurnoDAO turnoDAO = factory.getTurnoDAO();
			
			try {
				utenteDAO.createTable();
				utenteDAO.insert(utenteRossi);
				strutturaDAO.createTable();
				strutturaDAO.insert(strMis);
				turnoDAO.createTable();
				turnoDAO.insert(turno);
				
				FatturaDAO fatturaDAO = factory.getFatturaDAO();
				fatturaDAO.createTable();
				
				fatturaDAO.insert(fattura);
				Fattura temp = fatturaDAO.get(fattura.getIdFattura());
				
				System.out.println("1) " + fattura.equals(temp));
				
				fatturaDAO.update(fattura);
				temp = fatturaDAO.get(fattura.getIdFattura());
				
				System.out.println("2) " + fattura.equals(temp));
				
				fatturaDAO.dropTable();
				
				//assertThrows(SQLException.class, () -> tempoDAO.get(this.tempo.getIdTempo()));
				//assertThrows(SQLException.class, () -> tempoDAO.get(this.turno.getId()));
				
				//assertThrows(SQLException.class, () -> turnoDAO.dropTable());
				
			} catch (SQLException e) {
				e.printStackTrace();
				flagMsg = e.getMessage();
				
			} finally {
				turnoDAO.dropTable();;
				utenteDAO.dropTable();
				strutturaDAO.dropTable();
			}
		} catch(SQLException e) {
			
		}
		
		if(flagMsg != null) System.err.println(flagMsg);
	}
}
