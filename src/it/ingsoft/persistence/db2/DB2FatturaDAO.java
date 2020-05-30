package it.ingsoft.persistence.db2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.fattura.FatturaDAO;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.utente.Utente;

public class DB2FatturaDAO implements FatturaDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE FATTURA (" +
					   "IDFATTURA INT NOT NULL PRYMARY KEY," +
					   "IDTURNO INT NOT NULL REFERENCES(TURNO)," +
					   "IDUTENTE VARCHAR(20) NOT NULL REFERENCES(UTENTE);";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE FATTURA;";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void insert(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		for(Turno t : fattura.getAcquisti())
		{
			PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO TURNI (IDFATTURA, " +
																							 "IDTURNO, " +
																							 "IDUTENTE, " +
																							 ") VALUES (?,?,?);");
			
			prepStatement.setInt(1, fattura.getIdFattura());
			prepStatement.setInt(2, t.getId());
			prepStatement.setString(3, fattura.getUtente().getCodiceFiscale());
			
			prepStatement.executeUpdate();
		}
		
		DB2FactoryDAO.closeConnection();
	}

	@Override @Deprecated
	public void update(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		for(Turno t : fattura.getAcquisti())
		{
			PreparedStatement prepStatement = connection.prepareStatement("UPDATE TEMPI SET " +
																						   "IDTURNO = ?, " +
																						   "WHERE IDFATTURA = ? AND IDUTENTE = ?;");
			
			prepStatement.setInt(1, t.getId());
			prepStatement.setInt(2, fattura.getIdFattura());
			prepStatement.setString(3, fattura.getUtente().getCodiceFiscale());
			
			prepStatement.executeUpdate();
		}
		
		DB2FactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Fattura fattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM FATTURA WHERE IDFATTURA = ?;");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public Fattura get(int idFattura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM FATTURA WHERE IDFATTURA = ?;");
		
		ResultSet resS = prepStatement.executeQuery();
		
		Fattura result = new Fattura();
		result.setAcquisti(new ArrayList<Turno>());
		
		while(resS.next())
		{
			DB2FactoryDAO factory = new DB2FactoryDAO();
			Turno turno = factory.getTurnoDAO().get(resS.getInt("IDTURNO"));
			Struttura struttura = turno.getStruttura();
			Utente utente = factory.getUtenteDAO().get(resS.getString("IDUTENTE"));
			
			result.setIdFattura(idFattura);
			result.setStruttura(struttura);
			result.setUtente(utente);
			result.getAcquisti().add(turno);
		}
		
		return result;
	}
}
