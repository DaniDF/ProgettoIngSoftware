package it.ingsoft.model.tempo;

import java.sql.SQLException;

public interface TempoDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	void insert(Tempo utente) throws SQLException;
	void update(Tempo utente) throws SQLException;
	void delete(Tempo utente) throws SQLException;
	Tempo get(int id) throws SQLException;
}
