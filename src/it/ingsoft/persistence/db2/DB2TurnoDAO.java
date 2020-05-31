package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDateTime;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;

public class DB2TurnoDAO implements TurnoDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE TURNI (" +
					   "IDTURNO INT NOT NULL PRIMARY KEY," +
					   "IDSTRUTTURA VARCHAR(20) NOT NULL REFERENCES STRUTTURA(PARTITAIVA)," +
					   "DATAINIZIO DATE NOT NULL," +
					   "ORAINIZIO TIME NOT NULL," +
					   "DATAFINE DATE NOT NULL," +
					   "ORAFINE TIME NOT NULL," +
					   "POSTIDISPONIBILI INT NOT NULL," +
					   "PREZZO FLOAT NOT NULL)";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE TURNI";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void insert(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TURNI (IDTURNO, " +
																						 "IDSTRUTTURA, " +
																						 "DATAINIZIO, " +
																						 "ORAINIZIO, " +
																						 "DATAFINE, " +
																						 "ORAFINE, " +
																						 "POSTIDISPONIBILI," +
																						 "PREZZO" +
																						 ") VALUES (?,?,?,?,?,?,?,?)");
		
		Date dataInizio = Date.valueOf(turno.getInizio().toLocalDate());
		Time oraInizio = Time.valueOf(turno.getInizio().toLocalTime());
		Date dataFine = Date.valueOf(turno.getFine().toLocalDate());
		Time oraFine = Time.valueOf(turno.getFine().toLocalTime());
		
		
		prepStatement.setInt(1, turno.getId());
		prepStatement.setString(2, turno.getStruttura().getPartitaIva());
		prepStatement.setDate(3, dataInizio);
		prepStatement.setTime(4, oraInizio);
		prepStatement.setDate(5, dataFine);
		prepStatement.setTime(6, oraFine);
		prepStatement.setInt(7, turno.getPostiDisponibili());
		prepStatement.setFloat(8, turno.getPrezzo());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
		
		TempoDAO tempiDAO = new DB2FactoryDAO().getTempoDAO();
		
		for(Tempo t : turno.getTempi())
		{
			tempiDAO.insert(t,turno);
		}
	}

	@Override
	public void update(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE TEMPI SET " +
																					   "IDSTRUTTURA = ?, " +
																					   "DATAINIZIO = ?, " +
																					   "ORAINIZIO = ?, " +
																					   "DATAFINE = ?, " +
																					   "ORAFINE = ?, " +
																					   "POSTIDISPONIBILI = ?, " +
																					   "PREZZO = ? " +
																					   "WHERE IDTURNO = ?");
		
		Date dataInizio = Date.valueOf(turno.getInizio().toLocalDate());
		Time oraInizio = Time.valueOf(turno.getInizio().toLocalTime());
		Date dataFine = Date.valueOf(turno.getFine().toLocalDate());
		Time oraFine = Time.valueOf(turno.getFine().toLocalTime());
		
		
		prepStatement.setString(1, turno.getStruttura().getPartitaIva());
		prepStatement.setDate(2, dataInizio);
		prepStatement.setTime(3, oraInizio);
		prepStatement.setDate(4, dataFine);
		prepStatement.setTime(5, oraFine);
		prepStatement.setInt(6, turno.getPostiDisponibili());
		prepStatement.setInt(7, turno.getId());
		prepStatement.setFloat(8, turno.getPrezzo());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
		
		TempoDAO tempiDAO = new DB2FactoryDAO().getTempoDAO();
		
		for(Tempo t : turno.getTempi())
		{
			tempiDAO.update(t);
		}
	}

	@Override
	public void delete(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM TURNI WHERE IDTURNO = ?");
		
		prepStatement.setInt(1, turno.getId());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public Turno get(int idTurno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TURNI WHERE IDTURNO = ?");
		
		prepStatement.setInt(1, idTurno);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Turno result = new Turno();
		
		DB2FactoryDAO factory = new DB2FactoryDAO();
		Struttura struttura = factory.getStrutturaDAO().get(resS.getString("IDSTRUTTURA"));
		Date datIn = resS.getDate("DATAINIZIO");
		Time oraIn = resS.getTime("ORAINIZIO");
		Date datFi = resS.getDate("DATAFINE");
		Time oraFi = resS.getTime("ORAFINE");
		
		LocalDateTime inizio = LocalDateTime.of(datIn.toLocalDate(), oraIn.toLocalTime());
		LocalDateTime fine = LocalDateTime.of(datFi.toLocalDate(), oraFi.toLocalTime());
		
		result.setId(idTurno);
		result.setStruttura(struttura);
		result.setInizio(inizio);
		result.setFine(fine);
		result.setPostiDisponibili(resS.getInt("POSTIDISPONIBILI"));
		result.setPrezzo(resS.getFloat("PREZZO"));

		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		return result;
	}
}
