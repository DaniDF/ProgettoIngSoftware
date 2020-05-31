package it.ingsoft.persistence.db2;

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
import it.ingsoft.model.utente.Utente;

public class DB2TempoDAO implements TempoDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE TEMPI (" +
					   "IDTEMPO INT NOT NULL PRIMARY KEY," +
					   "IDUTENTE VARCHAR(20) NOT NULL REFERENCES UTENTE(CODICEFISCALE)," +
					   "IDTURNO INT NOT NULL REFERENCES TURNI(IDTURNO), " +	//Aggiunto
					   "VALORE BIGINT NOT NULL)";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE TEMPI";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void insert(Tempo tempo, Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TEMPI (IDTEMPO, " +
																						 "IDUTENTE, " +
																						 "IDTURNO, " +	//Aggiunto
																						 "VALORE" +
																						 ") VALUES (?,?,?,?)");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		prepStatement.setString(2, tempo.getUtente().getCodiceFiscale());
		prepStatement.setInt(3, turno.getId());	//Aggiunto
		prepStatement.setLong(4, tempo.getValore());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void update(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE TEMPI SET " +
																					   "IDUTENTE = ?, " +
																					   "VALORE = ? " +
																					   "WHERE IDTEMPO = ?");
		
		prepStatement.setString(1, tempo.getUtente().getCodiceFiscale());
		prepStatement.setLong(2, tempo.getValore());
		prepStatement.setInt(3, tempo.getIdTempo());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM TEMPI WHERE IDTEMPO = ?");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public Tempo get(int idTempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TEMPI WHERE IDTEMPO = ?");

		prepStatement.setInt(1, idTempo);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Tempo result = new Tempo();
		
		Utente utente = new DB2FactoryDAO().getUtenteDAO().get(resS.getString("IDUTENTE"));
		
		result.setIdTempo(idTempo);
		result.setUtente(utente);
		result.setValore(resS.getLong("VALORE"));

		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		return result;
	}
	
	@Override
	public List<Tempo> getByTurno(int idTurno) throws SQLException
	{
		List<Tempo> result = new ArrayList<>();
		
		Connection connection = DB2FactoryDAO.createConnection();
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
