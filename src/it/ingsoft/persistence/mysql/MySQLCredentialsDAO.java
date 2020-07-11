package it.ingsoft.persistence.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.security.CredentialsDAO;

public class MySQLCredentialsDAO implements CredentialsDAO {

	@Override
	public boolean checkCredentials(String username, String password) throws SQLException {
		PreparedStatement prepStatement = MySQLFactoryDAO.createConnection().prepareStatement("SELECT USERNAME FROM CREDENTIALS " +
																										"WHERE USERNAME = ? AND " +
																										"PASSWORD = ?");
		prepStatement.setString(1, username);
		prepStatement.setString(2, password);
		
		ResultSet resS = prepStatement.executeQuery();
		
		boolean result = resS.next();
		result = result && !resS.next();
		
		prepStatement.close();
		MySQLFactoryDAO.closeConnection();

		return result;
	}

	@Override
	public void addCredentials(String username, String password) throws SQLException {
		PreparedStatement prepStatement = MySQLFactoryDAO.createConnection().prepareStatement("INSERT INTO CREDENTIALS (" +
																								"USERNAME, " +
																								"PASSWORD) " +
																								"VALUES (?,?)");
		prepStatement.setString(1, username);
		prepStatement.setString(2, password);
		
		prepStatement.executeUpdate();
		prepStatement.close();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public List<String> getAllUsers() throws SQLException {
		Statement statement = MySQLFactoryDAO.createConnection().createStatement();
		
		ResultSet resS = statement.executeQuery("SELECT USERNAME FROM CREDENTIALS");
		List<String> result = new ArrayList<>();
		
		while(resS.next())
		{
			result.add(resS.getString("USERNAME"));
		}
		
		statement.close();
		MySQLFactoryDAO.closeConnection();
		
		return result;
	}

	@Override
	public void delUser(String username) throws SQLException {
		PreparedStatement prepStatement = MySQLFactoryDAO.createConnection().prepareStatement("DELETE FROM CREDENTIALS " +
																						 	  "WHERE " +
																							  "USERNAME = ?");
		prepStatement.setString(1, username);
		
		prepStatement.executeUpdate();
		prepStatement.close();
		MySQLFactoryDAO.closeConnection();
	}

}
