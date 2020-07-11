package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.relations.TurnoTempoMappingDAO;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.persistence.DBInstance;

public class DB2TurnoTempoMappingDAO implements TurnoTempoMappingDAO {
	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE TURNO_TEMPO_MAPPING ("
								+ "TURNO INT NOT NULL REFERENCES TURNI(IDTURNO), "
								+ "TEMPO INT NOT NULL REFERENCES TEMPI(IDTEMPO), "
								+ "CONSTRAINT PK PRIMARY KEY (TURNO,TEMPO))");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE TURNO_TEMPO_MAPPING");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Turno turno, Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TURNO_TEMPO_MAPPING ("
																			+ "TURNO, "
																			+ "TEMPO"
																			+ ") VALUES (?,?)");
		
		prepStatement.setInt(1, turno.getId());
		prepStatement.setInt(2, tempo.getIdTempo());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Turno turno, Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM TURNO_TEMPO_MAPPING WHERE "
																			+ "TURNO = ? AND TEMPO = ?");
		
		prepStatement.setInt(1, turno.getId());
		prepStatement.setInt(2, tempo.getIdTempo());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public List<Tempo> getTempi(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TURNO_TEMPO_MAPPING WHERE TURNO = ?");
		
		prepStatement.setInt(1, turno.getId());
		
		List<Tempo> result = new ArrayList<>();
		ResultSet resS = prepStatement.executeQuery();
		while(resS.next())
		{
			TempoDAO tempoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTempoDAO();
			Tempo newTempo = tempoDAO.get(resS.getInt("TEMPO"));
			
			if(newTempo != null) result.add(newTempo);
		}

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

	@Override
	public Turno getTurno(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TURNO_TEMPO_MAPPING WHERE TEMPO = ?");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		TurnoDAO turnoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoDAO();
		Turno result = turnoDAO.get(resS.getInt("TURNO"));

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
