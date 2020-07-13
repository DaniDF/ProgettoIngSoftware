package it.ingsoft.persistence;

import java.sql.SQLException;

import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.relations.FatturaTurnoMappingDAO;
import it.ingsoft.model.relations.StrutturaCredentialsMappingDAO;
import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.relations.TurnoTempoMappingDAO;
import it.ingsoft.model.relations.UtenteCredentialsMappingDAO;
import it.ingsoft.model.relations.UtenteFatturaMappingDAO;
import it.ingsoft.model.relations.UtenteTempoMappingDAO;
import it.ingsoft.model.security.CredentialsDAO;
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
		//else if(daoInstance.equals(DBInstance.MYSQL)) result = new MySQLFactoryDAO();
		else result = new DB2FactoryDAO();
			
		return result;
	}
	
	public abstract FatturaDAO getFatturaDAO() throws SQLException;
	public abstract StrutturaDAO getStrutturaDAO() throws SQLException;
	public abstract TempoDAO getTempoDAO() throws SQLException;
	public abstract TurnoDAO getTurnoDAO() throws SQLException;
	public abstract UtenteDAO getUtenteDAO() throws SQLException;
	public abstract CredentialsDAO getCredentialsDAO() throws SQLException;
	public abstract StrutturaTurnoMappingDAO getStrutturaTurnoMappingDAO() throws SQLException;
	public abstract TurnoTempoMappingDAO getTurnoTempoMappingDAO() throws SQLException;
	public abstract UtenteTempoMappingDAO getUtenteTempoMappingDAO() throws SQLException;
	public abstract FatturaTurnoMappingDAO getFatturaTurnoMappingDAO() throws SQLException;
	public abstract UtenteFatturaMappingDAO getUtenteFatturaMappingDAO() throws SQLException;
	public abstract UtenteCredentialsMappingDAO getUtenteCredentialsMappingDAO() throws SQLException;
	public abstract StrutturaCredentialsMappingDAO getStrutturaCredentialsMappingDAO() throws SQLException;
}
