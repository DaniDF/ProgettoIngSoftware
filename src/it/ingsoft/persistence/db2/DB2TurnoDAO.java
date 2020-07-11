package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;

import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.persistence.db2.proxy.DB2TurnoProxy;

public class DB2TurnoDAO implements TurnoDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE TURNI (" +
					   "IDTURNO INT NOT NULL PRIMARY KEY," +
					   "DATAINIZIO DATE," +
					   "ORAINIZIO TIME," +
					   "DATAFINE DATE," +
					   "ORAFINE TIME," +
					   "POSTIDISPONIBILI INT," +
					   "PREZZO FLOAT )";
		
		Statement statement = connection.createStatement();
		statement.execute(query);

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE TURNI";
		
		Statement statement = connection.createStatement();
		statement.execute(query);

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TURNI (IDTURNO, " +
																						 "DATAINIZIO, " +
																						 "ORAINIZIO, " +
																						 "DATAFINE, " +
																						 "ORAFINE, " +
																						 "POSTIDISPONIBILI," +
																						 "PREZZO" +
																						 ") VALUES (?,?,?,?,?,?,?)");
		
		Date dataInizio = null;
		Time oraInizio = null;
		Date dataFine = null;
		Time oraFine = null;		
		
		try { dataInizio = Date.valueOf(turno.getInizio().toLocalDate()); } catch(NullPointerException e) { dataInizio = null; }
		try { oraInizio = Time.valueOf(turno.getInizio().toLocalTime()); } catch(NullPointerException e) { oraInizio = null; }
		try { dataFine = Date.valueOf(turno.getFine().toLocalDate()); } catch(NullPointerException e) { dataFine = null; }
		try { oraFine = Time.valueOf(turno.getFine().toLocalTime()); } catch(NullPointerException e) { oraFine = null; }
		
		
		prepStatement.setInt(1, turno.getId());
		prepStatement.setDate(2, dataInizio);
		prepStatement.setTime(3, oraInizio);
		prepStatement.setDate(4, dataFine);
		prepStatement.setTime(5, oraFine);
		prepStatement.setInt(6, turno.getPostiDisponibili());
		prepStatement.setFloat(7, turno.getPrezzo());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void update(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE TURNI SET " +
																					   "DATAINIZIO = ?, " +
																					   "ORAINIZIO = ?, " +
																					   "DATAFINE = ?, " +
																					   "ORAFINE = ?, " +
																					   "POSTIDISPONIBILI = ?, " +
																					   "PREZZO = ? " +
																					   "WHERE IDTURNO = ?");
		
		Date dataInizio = null;
		Time oraInizio = null;
		Date dataFine = null;
		Time oraFine = null;		
		
		try { dataInizio = Date.valueOf(turno.getInizio().toLocalDate()); } catch(NullPointerException e) { dataInizio = null; }
		try { oraInizio = Time.valueOf(turno.getInizio().toLocalTime()); } catch(NullPointerException e) { oraInizio = null; }
		try { dataFine = Date.valueOf(turno.getFine().toLocalDate()); } catch(NullPointerException e) { dataFine = null; }
		try { oraFine = Time.valueOf(turno.getFine().toLocalTime()); } catch(NullPointerException e) { oraFine = null; }
		
		
		prepStatement.setDate(1, dataInizio);
		prepStatement.setTime(2, oraInizio);
		prepStatement.setDate(3, dataFine);
		prepStatement.setTime(4, oraFine);
		prepStatement.setInt(5, turno.getPostiDisponibili());
		prepStatement.setInt(6, turno.getId());
		prepStatement.setFloat(7, turno.getPrezzo());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM TURNI WHERE IDTURNO = ?");
		
		prepStatement.setInt(1, turno.getId());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public Turno get(int idTurno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TURNI WHERE IDTURNO = ?");
		
		prepStatement.setInt(1, idTurno);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Turno result = new DB2TurnoProxy(idTurno);
		
		Date datIn = resS.getDate("DATAINIZIO");
		Time oraIn = resS.getTime("ORAINIZIO");
		Date datFi = resS.getDate("DATAFINE");
		Time oraFi = resS.getTime("ORAFINE");
		
		LocalDateTime inizio = null;
		LocalDateTime fine = null;LocalDateTime.of(datFi.toLocalDate(), oraFi.toLocalTime());
		
		try { inizio = LocalDateTime.of(datIn.toLocalDate(), oraIn.toLocalTime()); } catch(NullPointerException e) { inizio = null; }
		try { fine = LocalDateTime.of(datFi.toLocalDate(), oraFi.toLocalTime()); } catch(NullPointerException e) { fine = null; }
		
		result.setInizio(inizio);
		result.setFine(fine);
		result.setPostiDisponibili(resS.getInt("POSTIDISPONIBILI"));
		result.setPrezzo(resS.getFloat("PREZZO"));

		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
