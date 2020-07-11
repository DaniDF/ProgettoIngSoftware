package it.ingsoft.model.security;

import java.sql.SQLException;
import java.util.List;

public interface CredentialsDAO {
	public boolean checkCredentials(String username, String password) throws SQLException;
	public void addCredentials(String username, String password) throws SQLException;
	public List<String> getAllUsers() throws SQLException;
	public void delUser(String username) throws SQLException;
}
