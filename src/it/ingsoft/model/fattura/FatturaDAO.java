package it.ingsoft.model.fattura;

import java.sql.SQLException;

public interface FatturaDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	void insert(Fattura utente) throws SQLException;
	void update(Fattura utente) throws SQLException;
	void delete(Fattura utente) throws SQLException;
	Fattura get(int idFattura) throws SQLException;
}
