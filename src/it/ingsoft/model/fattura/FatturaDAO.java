package it.ingsoft.model.fattura;

import java.sql.SQLException;

public interface FatturaDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Fattura utente) throws SQLException;
	public void update(Fattura utente) throws SQLException;
	public void delete(Fattura utente) throws SQLException;
	public Fattura get(int idFattura) throws SQLException;
}
