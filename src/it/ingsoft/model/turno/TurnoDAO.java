package it.ingsoft.model.turno;

import java.sql.SQLException;

public interface TurnoDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	void insert(Turno utente) throws SQLException;
	void update(Turno utente) throws SQLException;
	void delete(Turno utente) throws SQLException;
	Turno get(int id) throws SQLException;
}
