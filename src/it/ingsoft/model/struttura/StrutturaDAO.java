package it.ingsoft.model.struttura;

import java.sql.SQLException;

public interface StrutturaDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Struttura utente) throws SQLException;
	public void update(Struttura utente) throws SQLException;
	public void delete(Struttura utente) throws SQLException;
	public Struttura get(String partitaIva) throws SQLException;
}
