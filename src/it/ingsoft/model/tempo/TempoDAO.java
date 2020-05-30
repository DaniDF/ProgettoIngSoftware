package it.ingsoft.model.tempo;

import java.sql.SQLException;
import java.util.List;

public interface TempoDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Tempo utente) throws SQLException;
	public void update(Tempo utente) throws SQLException;
	public void delete(Tempo utente) throws SQLException;
	public Tempo get(int id) throws SQLException;
	public List<Tempo> getByTurno(int idTurno) throws SQLException;
}
