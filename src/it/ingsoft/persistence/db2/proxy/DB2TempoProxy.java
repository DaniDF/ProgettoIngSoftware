package it.ingsoft.persistence.db2.proxy;

import java.sql.SQLException;

import it.ingsoft.model.relations.UtenteTempoMappingDAO;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.db2.DB2FactoryDAO;

public class DB2TempoProxy extends Tempo {
	
	public DB2TempoProxy(int idTempo) {
		super();
		
		try
		{
			TempoDAO tempoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTempoDAO();
			Tempo newTempo = tempoDAO.get(idTempo);
			
			super.setIdTempo(idTempo);
			super.setValore(newTempo.getValore());
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare il tempo: " + e.getMessage());
			
			super.setIdTempo(idTempo);
			
			try
			{
				TempoDAO tempoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTempoDAO();
				tempoDAO.insert(this);
				
			} catch(SQLException ee) {
				System.err.println("Impossibile salvare il tempo: " + ee.getMessage());
			}
		}
	}
	
	@Override
	public Utente getUtente() {
		Utente result = null;
		
		try
		{
			UtenteTempoMappingDAO uteTemDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteTempoMappingDAO();
			result = uteTemDAO.getUtente(this);
			
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
			UtenteTempoMappingDAO uteTemDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteTempoMappingDAO();
			uteTemDAO.insert(utente, this);
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare l'utente: " + e.getMessage());
		}
	}
	
	@Override
	public void setIdTempo(int idTempo) {
		throw new InvalidUseException("Metodo non utilizzabile per la sincronia remota");
	}
	
	@Override
	public void setValore(long valore) {
		super.setValore(valore);
		this.update();
	}
	
	private void update()
	{
		try {
			TempoDAO tempoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTempoDAO();
			tempoDAO.update(this);
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare il tempo: " + e.getMessage());
		}
	}
}
