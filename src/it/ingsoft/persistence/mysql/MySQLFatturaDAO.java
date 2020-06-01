package it.ingsoft.persistence.mysql;

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

public class MySQLFatturaDAO implements FatturaDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "CREATE TABLE FATTURA (" +
					   "IDFATTURA INT NOT NULL," +
					   "IDTURNO INT NOT NULL REFERENCES TURNI(IDTURNO)," +
					   "IDUTENTE VARCHAR(20) NOT NULL REFERENCES UTENTE(CODICEFISCALE)," +
					   "CONSTRAINT PK PRIMARY KEY(IDFATTURA,IDTURNO) )";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "DROP TABLE FATTURA";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void insert(Fattura fattura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO FATTURA (IDFATTURA, " +
																						   "IDTURNO, " +
																						   "IDUTENTE" +
																						   ") VALUES (?,?,?)");
		
		for(Turno t : fattura.getAcquisti())
		{			
			prepStatement.setInt(1, fattura.getIdFattura());
			prepStatement.setInt(2, t.getId());
			prepStatement.setString(3, fattura.getUtente().getCodiceFiscale());
			
			prepStatement.executeUpdate();
		}
		
		MySQLFactoryDAO.closeConnection();
	}

	@Override @Deprecated
	public void update(Fattura fattura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE FATTURA SET " +
																					   "IDUTENTE = ? " +
																					   "WHERE IDFATTURA = ? AND IDTURNO = ?");
		
		for(Turno t : fattura.getAcquisti())
		{
			prepStatement.setString(1, fattura.getUtente().getCodiceFiscale());
			prepStatement.setInt(2, fattura.getIdFattura());
			prepStatement.setInt(3, t.getId());
			
			prepStatement.executeUpdate();
		}
		
		MySQLFactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Fattura fattura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM FATTURA WHERE IDFATTURA = ?");
		
		prepStatement.setInt(1, fattura.getIdFattura());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public Fattura get(int idFattura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM FATTURA WHERE IDFATTURA = ?");

		prepStatement.setInt(1, idFattura);
		
		ResultSet resS = prepStatement.executeQuery();
		
		Fattura result = new Fattura();
		result.setAcquisti(new ArrayList<Turno>());
		
		while(resS.next())
		{
			MySQLFactoryDAO factory = new MySQLFactoryDAO();
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
