package it.ingsoft.persistence.db2.proxy;

import java.sql.SQLException;

import it.ingsoft.model.relations.UtenteCredentialsMappingDAO;
import it.ingsoft.model.security.Credentials;
import it.ingsoft.model.security.CredentialsDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class DB2CredentialsProxyDAO extends Credentials {
	
	static
	{
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			CredentialsDAO credentialsDAO = factory.getCredentialsDAO();
			credentialsDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella credenziali");
		}
		
		try
		{
			UtenteCredentialsMappingDAO uteCreDAO = factory.getUtenteCredentialsMappingDAO();
			uteCreDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella relazione utenti e credenziali");
		}
	}
	
	public DB2CredentialsProxyDAO(String username)
	{
		super();
		super.setUsername(username);
	}
	
	public DB2CredentialsProxyDAO(Utente utente, String username, String password)
	{
		this(username);
		
		try
		{
			FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
			
			CredentialsDAO credentias = factory.getCredentialsDAO();
			credentias.addCredentials(username, password);
			
			UtenteCredentialsMappingDAO uteCreDAO = factory.getUtenteCredentialsMappingDAO();
			uteCreDAO.insert(utente, username);
			
		} catch (SQLException e) {
			System.err.println("Impossibile salvare le credenziali: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Override
	public void setUsername(String username)
	{
		throw new InvalidUseException("Metodo non utilizzabile per la sincronia remota");
	}
	
	public boolean checkCredentials(String password)
	{
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		boolean result = false; 
		
		try
		{
			CredentialsDAO credentials = factory.getCredentialsDAO();
			result = credentials.checkCredentials(this.getUsername(), password);
		} catch (SQLException e) {
			
		}
		
		return result;
	}
}
