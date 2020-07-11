package it.ingsoft.model.relations;

import java.sql.SQLException;
import java.util.List;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.turno.Turno;

public interface StrutturaTurnoMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Struttura struttura, Turno turno) throws SQLException;
	public void delete(Struttura struttura, Turno turno) throws SQLException;
	public List<Turno> getTurni(Struttura struttura) throws SQLException;
	public Struttura getStruttura(Turno turno) throws SQLException;
}
