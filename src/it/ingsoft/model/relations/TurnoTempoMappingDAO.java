package it.ingsoft.model.relations;

import java.sql.SQLException;
import java.util.List;

import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.turno.Turno;

public interface TurnoTempoMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Turno turno, Tempo tempo) throws SQLException;
	public void delete(Turno turno, Tempo tempo) throws SQLException;
	public List<Tempo> getTempi(Turno turno) throws SQLException;
	public Turno getTurno(Tempo tempo) throws SQLException;
}
