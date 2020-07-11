package it.ingsoft.model.relations;

import java.sql.SQLException;
import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.turno.Turno;

public interface FatturaTurnoMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Fattura fattura, Turno turno) throws SQLException;
	public void delete(Fattura fattura, Turno turno) throws SQLException;
	public List<Turno> getTurni(Fattura fattura) throws SQLException;
	public Fattura getFattura(Turno turno) throws SQLException;
}
