package it.ingsoft.model.utente;

import java.sql.SQLException;

public interface UtenteDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	void insert(Utente utente) throws SQLException;
	void update(Utente utente) throws SQLException;
	void delete(Utente utente) throws SQLException;
	Utente get(String codiceFiscale) throws SQLException;
}
