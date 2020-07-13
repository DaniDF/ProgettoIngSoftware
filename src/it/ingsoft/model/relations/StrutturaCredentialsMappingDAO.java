package it.ingsoft.model.relations;

import java.sql.SQLException;

import it.ingsoft.model.struttura.Struttura;

public interface StrutturaCredentialsMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Struttura struttura, String username) throws SQLException;
	public void delete(Struttura struttura) throws SQLException;
	public String getUsername(Struttura struttura) throws SQLException;
	public Struttura getStruttura(String username) throws SQLException;
}
