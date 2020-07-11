package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.relations.FatturaTurnoMappingDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.persistence.DBInstance;

public class DB2FatturaTurnoMappingDAO implements FatturaTurnoMappingDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE FATTURA_TURNO_MAPPING ("
								+ "FATTURA INT NOT NULL REFERENCES FATTURA(IDFATTURA), "
								+ "TURNO INT NOT NULL REFERENCES TURNI(IDTURNO), "
								+ "CONSTRAINT PK PRIMARY KEY (FATTURA,TURNO))");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE FATTURA_TURNO_MAPPING");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Fattura fattura, Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO FATTURA_TURNO_MAPPING ("
																			+ "FATTURA, "
																			+ "TURNO"
																			+ ") VALUES (?,?)");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		prepStatement.setInt(2, turno.getId());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Fattura fattura, Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM FATTURA_TURNO_MAPPING WHERE "
																			+ "FATTURA = ? AND TURNO = ?");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		prepStatement.setInt(2, turno.getId());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public List<Turno> getTurni(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM FATTURA_TURNO_MAPPING WHERE FATTURA = ?");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		List<Turno> result = new ArrayList<>();
		ResultSet resS = prepStatement.executeQuery();
		while(resS.next())
		{
			TurnoDAO turnoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoDAO();
			Turno newTurno = turnoDAO.get(resS.getInt("TURNO"));
			
			if(newTurno != null) result.add(newTurno);
		}

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

	@Override
	public Fattura getFattura(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM FATTURA_TURNO_MAPPING WHERE TURNO = ?");
		
		prepStatement.setInt(1, turno.getId());
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		FatturaDAO fatturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getFatturaDAO();
		Fattura result = fatturaDAO.get(resS.getInt("FATTURA"));

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

}
