package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.relations.UtenteTempoMappingDAO;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.tempo.TempoDAO;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;

public class DB2UtenteTempoMappingDAO implements UtenteTempoMappingDAO {
	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("CREATE TABLE UTENTE_TEMPO_MAPPING ("
								+ "UTENTE VARCHAR(20) NOT NULL REFERENCES UTENTE(CODICEFISCALE), "
								+ "TEMPO INT NOT NULL REFERENCES TEMPI(IDTEMPO), "
								+ "CONSTRAINT PK PRIMARY KEY (UTENTE,TEMPO))");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("DROP TABLE UTENTE_TEMPO_MAPPING");

		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void insert(Utente utente, Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO UTENTE_TEMPO_MAPPING ("
																			+ "UTENTE, "
																			+ "TEMPO"
																			+ ") VALUES (?,?)");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		prepStatement.setInt(2, tempo.getIdTempo());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Utente utente, Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM UTENTE_TEMPO_MAPPING WHERE "
																			+ "UTENTE = ? AND TEMPO = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		prepStatement.setInt(2, tempo.getIdTempo());
		
		prepStatement.execute();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public List<Tempo> getTempi(Utente utente) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM UTENTE_TEMPO_MAPPING WHERE UTENTE = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		
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
	public Utente getUtente(Tempo tempo) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM TURNO_TEMPO_MAPPING WHERE TEMPO = ?");
		
		prepStatement.setInt(1, tempo.getIdTempo());
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		UtenteDAO utenteDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteDAO();
		Utente result = utenteDAO.get(resS.getString("UTENTE"));

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
