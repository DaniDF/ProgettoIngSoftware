package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.persistence.DBInstance;

public class DB2StrutturaTurnoMappingDAO implements StrutturaTurnoMappingDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE STRUTTURA_TURNO_MAPPING ("
								+ "STRUTTURA VARCHAR(30) NOT NULL REFERENCES STRUTTURA(PARTITAIVA), "
								+ "TURNO INT NOT NULL REFERENCES TURNI(IDTURNO), "
								+ "CONSTRAINT PK PRIMARY KEY (STRUTTURA,TURNO))");
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE STRUTTURA_TURNO_MAPPING");
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Struttura struttura, Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO STRUTTURA_TURNO_MAPPING ("
																			+ "STRUTTURA, "
																			+ "TURNO"
																			+ ") VALUES (?,?)");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		prepStatement.setInt(2, turno.getId());
		
		prepStatement.execute();
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Struttura struttura, Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM STRUTTURA_TURNO_MAPPING WHERE "
																			+ "STRUTTURA = ? AND TURNO = ?");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		prepStatement.setInt(2, turno.getId());
		
		prepStatement.execute();
		
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public List<Turno> getTurni(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA_TURNO_MAPPING WHERE STRUTTURA = ?");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		
		List<Turno> result = new ArrayList<>();
		ResultSet resS = prepStatement.executeQuery();
		while(resS.next())
		{
			TurnoDAO turnoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoDAO();
			Turno newTurno = turnoDAO.get(resS.getInt("TURNO"));
			
			if(newTurno != null) result.add(newTurno);
		}
		
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}

	@Override
	public Struttura getStruttura(Turno turno) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA_TURNO_MAPPING WHERE TURNO = ?");
		
		prepStatement.setInt(1, turno.getId());
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		StrutturaDAO strutturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaDAO();
		Struttura result = strutturaDAO.get(resS.getString("STRUTTURA"));
		
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
