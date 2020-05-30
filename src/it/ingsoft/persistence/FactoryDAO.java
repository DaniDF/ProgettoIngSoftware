package it.ingsoft.persistence;

import java.sql.SQLException;

import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.db2.DB2FactoryDAO;

public abstract class FactoryDAO {

	public static FactoryDAO getDAOFactory(DBInstance daoInstance)
	{
		FactoryDAO result = null;
		if(daoInstance.equals(DBInstance.DB2)) result = new DB2FactoryDAO();
		else result = new DB2FactoryDAO();
			
		return result;
	}
	
	public abstract FatturaDAO getFatturaDAO() throws SQLException;
	public abstract StrutturaDAO getStrutturaDAO() throws SQLException;
	public abstract TempoDAO getTempoDAO() throws SQLException;
	public abstract TurnoDAO getTurnoDAO() throws SQLException;
	public abstract UtenteDAO getUtenteDAO() throws SQLException;
}
