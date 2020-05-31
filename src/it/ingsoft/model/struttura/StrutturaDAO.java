package it.ingsoft.model.struttura;

import java.sql.SQLException;

public interface StrutturaDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	void insert(Struttura utente) throws SQLException;
	void update(Struttura utente) throws SQLException;
	void delete(Struttura utente) throws SQLException;
	Struttura get(String partitaIva) throws SQLException;
}
