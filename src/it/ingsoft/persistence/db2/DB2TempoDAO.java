package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.persistence.db2.proxy.DB2TempoProxy;

public class DB2TempoDAO implements TempoDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE TEMPI (" +
					   "IDTEMPO INT NOT NULL PRIMARY KEY," +
					   "VALORE BIGINT )";
		
		Statement statement = connection.createStatement();
		statement.execute(query);

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE TEMPI";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		
		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TEMPI (IDTEMPO, " +
																						 "VALORE" +
																						 ") VALUES (?,?)");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		prepStatement.setLong(2, tempo.getValore());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void update(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE TEMPI SET " +
																					   "VALORE = ? " +
																					   "WHERE IDTEMPO = ?");
		prepStatement.setLong(1, tempo.getValore());
		prepStatement.setInt(2, tempo.getIdTempo());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
	}

	@Override
	public void delete(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM TEMPI WHERE IDTEMPO = ?");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public Tempo get(int idTempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TEMPI WHERE IDTEMPO = ?");

		prepStatement.setInt(1, idTempo);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Tempo result = new DB2TempoProxy(idTempo);
		
		result.setValore(resS.getLong("VALORE"));

		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
