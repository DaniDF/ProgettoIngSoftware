package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.turno.Turno;

public class DB2StrutturaTurnoDAO implements StrutturaTurnoMappingDAO {

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
	public void insert(Struttura struttura, Turno turno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Struttura struttura, Turno turno) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Turno> getTurni(Struttura struttura) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Turno getStruttura(Turno turni) {
		// TODO Auto-generated method stub
		return null;
	}

}
