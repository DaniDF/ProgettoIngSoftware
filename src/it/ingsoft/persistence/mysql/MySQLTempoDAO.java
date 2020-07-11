package it.ingsoft.persistence.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.Turno;

public class MySQLTempoDAO implements TempoDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "CREATE TABLE TEMPI (" +
					   "IDTEMPO INT NOT NULL PRIMARY KEY," +
					   "VALORE BIGINT NOT NULL)";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "DROP TABLE TEMPI";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void insert(Tempo tempo, Turno turno) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TEMPI (IDTEMPO, " +
																						 "VALORE" +
																						 ") VALUES (?,?)");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		prepStatement.setString(2, tempo.getUtente().getCodiceFiscale());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void update(Tempo tempo) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE TEMPI SET " +
																					   "VALORE = ? " +
																					   "WHERE IDTEMPO = ?");
		prepStatement.setLong(1, tempo.getValore());
		prepStatement.setInt(2, tempo.getIdTempo());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Tempo tempo) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM TEMPI WHERE IDTEMPO = ?");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public Tempo get(int idTempo) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TEMPI WHERE IDTEMPO = ?");

		prepStatement.setInt(1, idTempo);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Tempo result = new Tempo();
		
		result.setIdTempo(idTempo);
		result.setValore(resS.getLong("VALORE"));

		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		return result;
	}
	
	@Override
	public List<Tempo> getByTurno(int idTurno) throws SQLException
	{
		List<Tempo> result = new ArrayList<>();
		
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT IDTEMPO FROM TEMPI WHERE IDTURNO = ?");

		prepStatement.setInt(1, idTurno);		
		
		ResultSet resS = prepStatement.executeQuery();
		
		while(resS.next())
		{
			int idTempo = resS.getInt("IDTEMPO");
			result.add(this.get(idTempo));
		}
		
		return result;
	}
}
