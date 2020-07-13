package it.ingsoft.model.security;

import java.sql.SQLException;
import java.util.List;

public interface CredentialsDAO {
	void createTable() throws SQLException;
	void dropTable() throws SQLException;
	
	boolean checkCredentials(String username, String password) throws SQLException;
	void addCredentials(String username, String password) throws SQLException;
	List<String> getAllUsers() throws SQLException;
	void delUser(String username) throws SQLException;
}
