package it.ingsoft.persistence.mysql;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;

public class MySQLStrutturaDAO implements StrutturaDAO {
	
	@Override
	public void createTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "CREATE TABLE STRUTTURA (" +
					   "PARTITAIVA VARCHAR(30) NOT NULL PRIMARY KEY," +
					   "NOMESTRUTTURA VARCHAR(20) NOT NULL," +
					   "FOTOFATTURAVALIDA VARCHAR(500) NOT NULL," +
					   "IBAN VARCHAR(30) NOT NULL," +
					   "NAZIONE VARCHAR(20) NOT NULL," +
					   "PROVINCIA VARCHAR(20) NOT NULL," +
					   "CITTA VARCHAR(20) NOT NULL," +
					   "VIA VARCHAR(50) NOT NULL," +
					   "NUMEROCIVICO VARCHAR(5) NOT NULL," +
					   "CAP VARCHAR(5) NOT NULL)";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "DROP TABLE STRUTTURA";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void insert(Struttura struttura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO STRUTTURA (PARTITAIVA, " +
																							 "NOMESTRUTTURA, " +
																							 "FOTOFATTURAVALIDA, " +
																							 "IBAN, " +
																							 "NAZIONE, " +
																							 "PROVINCIA, " +
																							 "CITTA, " +
																							 "VIA, " +
																							 "NUMEROCIVICO, " +
																							 "CAP" +
																							 ") VALUES (?,?,?,?,?,?,?,?,?,?)");
		
		String cap = String.copyValueOf(struttura.getCap());
		
		prepStatement.setString(1, struttura.getPartitaIva());
		prepStatement.setString(2, struttura.getNomeStruttura());
		prepStatement.setString(3, struttura.getFotoFatturaValida().getAbsolutePath());
		prepStatement.setString(4, struttura.getIban());
		prepStatement.setString(5, struttura.getNazione());
		prepStatement.setString(6, struttura.getProvincia());
		prepStatement.setString(7, struttura.getCitta());
		prepStatement.setString(8, struttura.getVia());
		prepStatement.setString(9, struttura.getNumeroCivico());
		prepStatement.setString(10, cap);
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void update(Struttura struttura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE STRUTTURA SET " +
																						"NOMESTRUTTURA = ?, " +
																						"FOTOFATTURAVALIDA = ?, " +
																						"IBAN = ?, " +
																						"NAZIONE = ?, " +
																						"PROVINCIA = ?, " +
																						"CITTA = ?, " +
																						"VIA = ?, " +
																						"NUMEROCIVICO = ?, " +
																						"CAP = ?" +
																						"WHERE PARTITAIVA = ?");
		
		String cap = String.copyValueOf(struttura.getCap());
		
		prepStatement.setString(1, struttura.getNomeStruttura());
		prepStatement.setString(2, struttura.getFotoFatturaValida().getAbsolutePath());
		prepStatement.setString(3, struttura.getIban());
		prepStatement.setString(4, struttura.getNazione());
		prepStatement.setString(5, struttura.getProvincia());
		prepStatement.setString(6, struttura.getCitta());
		prepStatement.setString(7, struttura.getVia());
		prepStatement.setString(8, struttura.getNumeroCivico());
		prepStatement.setString(9, cap);
		prepStatement.setString(10, struttura.getPartitaIva());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Struttura struttura) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM STRUTTURA WHERE PARTITAIVA = ?");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public Struttura get(String partitaIva) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA WHERE PARTITAIVA = ?");
		
		prepStatement.setString(1, partitaIva);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Struttura result = new Struttura();
		
		char[] cap = resS.getString("CAP").toCharArray();
		
		result.setPartitaIva(partitaIva);
		result.setNomeStruttura(resS.getString("NOMESTRUTTURA"));
		result.setFotoFatturaValida(new File(resS.getString("FOTOFATTURAVALIDA")));
		result.setIban(resS.getString("IBAN"));
		result.setNazione(resS.getString("NAZIONE"));
		result.setProvincia(resS.getString("PROVINCIA"));
		result.setCitta(resS.getString("CITTA"));
		result.setVia(resS.getString("VIA"));
		result.setNumeroCivico(resS.getString("NUMEROCIVICO"));
		result.setCap(cap);
		
		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		return result;
	}
}
