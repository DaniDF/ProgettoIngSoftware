package it.ingsoft.persistence.db2.proxy;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.relations.FatturaTurnoMappingDAO;
import it.ingsoft.model.relations.UtenteFatturaMappingDAO;
import it.ingsoft.model.relations.UtenteTempoMappingDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;
import it.ingsoft.persistence.db2.DB2FactoryDAO;

public class DB2FatturaProxy extends Fattura {
	
	static
	{
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			FatturaDAO fatturaDAO = factory.getFatturaDAO();
			fatturaDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella fatture");
		}
		
		try
		{
			FatturaTurnoMappingDAO fatTurDAO = factory.getFatturaTurnoMappingDAO();
			fatTurDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella relazione fatture e turni");
		}
		
		try
		{
			UtenteTempoMappingDAO uteFatDAO = factory.getUtenteTempoMappingDAO();
			uteFatDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella relazione utenti e fatture");
		}
	}
	
	public DB2FatturaProxy(int idFattura) {
		super();
		
		try
		{
			FatturaDAO fatturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getFatturaDAO();
			Fattura newFattura = fatturaDAO.get(idFattura);
			
			super.setIdFattura(idFattura);
			super.setMetodoPagamento(newFattura.getMetodoPagamento());
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare la fattura: " + e.getMessage());
			
			super.setIdFattura(idFattura);
			
			try
			{
				FatturaDAO fatturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getFatturaDAO();
				fatturaDAO.insert(this);
				
			} catch(SQLException ee) {
				System.err.println("Impossibile salvare il fattura: " + ee.getMessage());
			}
		}
	}
	
	@Override
	public List<Turno> getAcquisti() {
		List<Turno> result = null;
		
		try
		{
			FatturaTurnoMappingDAO fatTurDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getFatturaTurnoMappingDAO();
			result = fatTurDAO.getTurni(this);
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i turni: " + e.getMessage());
			result = new ArrayList<>();
		}
		
		return result;
	}	
	
	@Override
	public void setAcquisti(List<Turno> acquisti) {
		super.setAcquisti(acquisti);
		
		try
		{
			FatturaTurnoMappingDAO fatTurDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getFatturaTurnoMappingDAO();
			
			for(Turno t : acquisti)
			{	
				fatTurDAO.insert(this, t);
			}
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i turni: " + e.getMessage());
		}
	}
	
	@Override
	public Utente getUtente() {
		Utente result = null;
		
		try
		{
			UtenteFatturaMappingDAO uteFatDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteFatturaMappingDAO();
			result = uteFatDAO.getUtente(this);
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare l'utente: " + e.getMessage());
			result = null;
		}
		
		return result;
	}
	
	@Override
	public void setUtente(Utente utente) {
		super.setUtente(utente);
		
		try
		{
			UtenteFatturaMappingDAO uteFatDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteFatturaMappingDAO();
			uteFatDAO.insert(utente, this);
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i turni: " + e.getMessage());
		}
	}
}
