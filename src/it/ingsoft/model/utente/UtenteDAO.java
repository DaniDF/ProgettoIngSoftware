package it.ingsoft.model.utente;

import java.sql.SQLException;

public interface UtenteDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Utente utente) throws SQLException;
	public void update(Utente utente) throws SQLException;
	public void delete(Utente utente) throws SQLException;
	public Utente get(String codiceFiscale) throws SQLException;
}
