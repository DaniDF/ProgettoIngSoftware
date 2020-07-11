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
import it.ingsoft.persistence.db2.proxy.DB2FatturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2StrutturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2TempoProxy;
import it.ingsoft.persistence.db2.proxy.DB2TurnoProxy;
import it.ingsoft.persistence.db2.proxy.DB2UtenteProxy;

public class TestMain {
	
	public static void main(String[] args)
	{
		Struttura struttura1 = new Struttura();
		struttura1.setPartitaIva("struttura 1");
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
		
		Utente utente1 = new Utente();
		utente1.setCodiceFiscale("Utente 1");
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
		
		Tempo tempo1 = new Tempo();
		tempo1.setIdTempo(1);
		tempo1.setValore(1000);
		
		Turno turno1 = new Turno();
		turno1.setId(1);
		turno1.setInizio(LocalDateTime.now());
		turno1.setFine(LocalDateTime.now().plusHours(2));
		turno1.setPostiDisponibili(100);
		turno1.setPrezzo(10.3F);
		turno1.setTempi(new ArrayList<>());
		
		Fattura fattura1 = new Fattura();
		fattura1.setIdFattura(1);
		
		try
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
		
			strutturaDAO.createTable();
			utenteDAO.createTable();
			tempoDAO.createTable();
			turnoDAO.createTable();
			fatturaDAO.createTable();
			strTurDAO.createTable();
			turTemDAO.createTable();
			uteTemDAO.createTable();
			uteFatDAO.createTable();
			fatTurDAO.createTable();
			
			Struttura strutturaD = new DB2StrutturaProxy("stuttura1");
			strutturaD.setNomeStruttura("Struttura 1");
			strutturaD.setFotoFatturaValida(new File("./temp.txt"));
			strutturaD.setIban("struttura 1");
			strutturaD.setNazione("struttura 1");
			strutturaD.setProvincia("struttura 1");
			strutturaD.setCitta("struttura 1");
			strutturaD.setVia("struttura 1");
			strutturaD.setNumeroCivico("str 1");
			strutturaD.setCap("12".toCharArray());
			strutturaD.setTurni(new ArrayList<>());
			
			Tempo tempoD = new DB2TempoProxy(2);
			tempoD.setValore(2000);
			
			Utente utenteD = new DB2UtenteProxy("utente2");
			utenteD.setCap("123".toCharArray());
			utenteD.setCitta("utente1");
			utenteD.setCognome("utente1");
			utenteD.setDataDiNascita(LocalDate.now());
			utenteD.setLuogoDiNascita("utente1");
			utenteD.setNazione("utente1");
			utenteD.setNome("utente1");
			utenteD.setNumeroCivico("Ute 1");
			utenteD.setNumeroDiTelefono("utente 1");
			utenteD.setProvincia("utente 1");
			utenteD.setVia("utente 1");
			
			Turno turnoD = new DB2TurnoProxy(2);
			turnoD.setInizio(LocalDateTime.now());
			turnoD.setFine(LocalDateTime.now().plusHours(2));
			turnoD.setPostiDisponibili(100);
			turnoD.setPrezzo(10.3F);
			turnoD.setTempi(new ArrayList<>());
			
			Fattura fatturaD = new DB2FatturaProxy(2);
			
			
			
			tempoD.setUtente(utenteD);
			turnoD.setStruttura(strutturaD);
			List<Tempo> tempi = turnoD.getTempi();
			tempi.add(tempoD);
			turnoD.setTempi(tempi);
			fatturaD.setUtente(utenteD);
			List<Turno> turni = fatturaD.getAcquisti();
			turni.add(turnoD);
			fatturaD.setAcquisti(turni);
			
			
			/*
			strutturaDAO.insert(struttura1);
			strutturaDAO.update(struttura1);
			
			utenteDAO.insert(utente1);
			utenteDAO.update(utente1);
			
			tempoDAO.insert(tempo1);
			tempoDAO.update(tempo1);
			
			turnoDAO.insert(turno1);
			turnoDAO.update(turno1);
			
			fatturaDAO.insert(fattura1);
			//fatturaDAO.update(fattura1);
			
			strTurDAO.insert(struttura1,turno1);
			
			turTemDAO.insert(turno1, tempo1);
			
			uteTemDAO.insert(utente1, tempo1);
			
			uteFatDAO.insert(utente1, fattura1);
			
			fatTurDAO.insert(fattura1, turno1);
			*/
			
			/*
			fatTurDAO.delete(fattura1, turno1);
			uteFatDAO.delete(utente1, fattura1);
			uteTemDAO.delete(utente1, tempo1);
			turTemDAO.delete(turno1, tempo1);
			strTurDAO.delete(struttura1, turno1);
			fatturaDAO.delete(fattura1);
			turnoDAO.delete(turno1);
			tempoDAO.delete(tempo1);
			utenteDAO.delete(utente1);
			strutturaDAO.delete(struttura1);*/
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
