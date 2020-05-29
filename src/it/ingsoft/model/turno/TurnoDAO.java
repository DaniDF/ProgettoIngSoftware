package it.ingsoft.model.turno;

import java.sql.SQLException;

public interface TurnoDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Turno utente) throws SQLException;
	public void update(Turno utente) throws SQLException;
	public void delete(Turno utente) throws SQLException;
	public Turno get(int id) throws SQLException;
}
