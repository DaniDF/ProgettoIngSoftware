package it.ingsoft.test;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.relations.FatturaTurnoMappingDAO;
import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.relations.TurnoTempoMappingDAO;
import it.ingsoft.model.relations.UtenteFatturaMappingDAO;
import it.ingsoft.model.relations.UtenteTempoMappingDAO;
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
import it.ingsoft.persistence.db2.proxy.DB2CredentialsProxyDAO;
import it.ingsoft.persistence.db2.proxy.DB2FatturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2StrutturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2TempoProxy;
import it.ingsoft.persistence.db2.proxy.DB2TurnoProxy;
import it.ingsoft.persistence.db2.proxy.DB2UtenteProxy;

public class TestMain {
	
	public static void main(String[] args)
	{
		/*try
		{
			FactoryDAO factoryDAO = FactoryDAO.getDAOFactory(DBInstance.DB2);
			StrutturaDAO strutturaDAO = factoryDAO.getStrutturaDAO();
			UtenteDAO utenteDAO = factoryDAO.getUtenteDAO();
			TempoDAO tempoDAO = factoryDAO.getTempoDAO();
			TurnoDAO turnoDAO = factoryDAO.getTurnoDAO();
			FatturaDAO fatturaDAO = factoryDAO.getFatturaDAO();
			StrutturaTurnoMappingDAO strTurDAO = factoryDAO.getStrutturaTurnoMappingDAO();
			TurnoTempoMappingDAO turTemDAO = factoryDAO.getTurnoTempoMappingDAO();
			UtenteTempoMappingDAO uteTemDAO = factoryDAO.getUtenteTempoMappingDAO();
			UtenteFatturaMappingDAO uteFatDAO = factoryDAO.getUtenteFatturaMappingDAO();
			FatturaTurnoMappingDAO fatTurDAO = factoryDAO.getFatturaTurnoMappingDAO();
			
			try { strutturaDAO.dropTable(); } catch (SQLException e) { }
			try { utenteDAO.dropTable(); } catch (SQLException e) { }
			try { tempoDAO.dropTable(); } catch (SQLException e) { }
			try { turnoDAO.dropTable(); } catch (SQLException e) { }
			try { fatturaDAO.dropTable(); } catch (SQLException e) { }
			try { strTurDAO.dropTable(); } catch (SQLException e) { }
			try { turTemDAO.dropTable(); } catch (SQLException e) { }
			try { uteTemDAO.dropTable(); } catch (SQLException e) { }
			try { uteFatDAO.dropTable(); } catch (SQLException e) { }
			try { fatTurDAO.dropTable(); } catch (SQLException e) { }
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(-1);
		}*/
		
		Struttura struttura1 = new DB2StrutturaProxy("struttura 1");
		struttura1.setNomeStruttura("Struttura 1");
		struttura1.setFotoFatturaValida(new File("./temp.txt"));
		struttura1.setIban("struttura 1");
		struttura1.setNazione("struttura 1");
		struttura1.setProvincia("struttura 1");
		struttura1.setCitta("struttura 1");
		struttura1.setVia("struttura 1");
		struttura1.setNumeroCivico("str 1");
		struttura1.setCap("12".toCharArray());
		struttura1.setTurni(new ArrayList<>());
		
		Utente utente1 = new DB2UtenteProxy("Utente 1");
		utente1.setNome("utente 1");
		utente1.setCognome("utente 1");
		utente1.setDataDiNascita(LocalDate.now());
		utente1.setLuogoDiNascita("utente 1");
		utente1.setNumeroDiTelefono("utente 1");
		utente1.setNazione("utente 1");
		utente1.setProvincia("utente 1");
		utente1.setCitta("utente 1");
		utente1.setVia("utente 1");
		utente1.setNumeroCivico("ut 1");
		utente1.setCap("123".toCharArray());
		
		Tempo tempo1 = new DB2TempoProxy(1);
		tempo1.setValore(1000);
		
		Turno turno1 = new DB2TurnoProxy(1);
		turno1.setInizio(LocalDateTime.now());
		turno1.setFine(LocalDateTime.now().plusHours(2));
		turno1.setPostiDisponibili(100);
		turno1.setPrezzo(10.3F);
		turno1.setTempi(new ArrayList<>());
		
		Fattura fattura1 = new DB2FatturaProxy(1);
		
		DB2CredentialsProxyDAO credentials = new DB2CredentialsProxyDAO(utente1, "dani", "dani");
		
		credentials = new DB2CredentialsProxyDAO("dani");
		if(!credentials.checkCredentials("dani")) throw new IllegalArgumentException("Errore chek password corretto");
		if(credentials.checkCredentials("adcv")) throw new IllegalArgumentException("Errore chek password errato");
	}
}
