package it.ingsoft.model.relations;

import java.sql.SQLException;

import it.ingsoft.model.utente.Utente;

public interface UtenteCredentialsMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Utente utente, String username) throws SQLException;
	public void delete(Utente utente) throws SQLException;
	public String getUsername(Utente utente) throws SQLException;
	public Utente getUtente(String username) throws SQLException;
}
