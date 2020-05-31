package it.ingsoft.model.tempo;

import java.sql.SQLException;
import java.util.List;

import it.ingsoft.model.turno.Turno;

public interface TempoDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	void insert(Tempo utente, Turno turno) throws SQLException;
	void update(Tempo utente) throws SQLException;
	void delete(Tempo utente) throws SQLException;
	Tempo get(int id) throws SQLException;
	List<Tempo> getByTurno(int idTurno) throws SQLException;
}
