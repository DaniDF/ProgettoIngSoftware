package it.ingsoft.persistence.db2;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;

public class DB2StrutturaDAO implements StrutturaDAO {
	
	@Override
	public void createTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "CREATE TABLE STRUTTURA (" +
					   "PARTITAIVA VARCHAR(30) NOT NULL PRYMARY KEY," +
					   "NOMESTRUTTURA VARCHAR(20) NOT NULL," +
					   "FOTOFATTURAVALIDA VARCHAR(20) NOT NULL," +
					   "IBAN DATE NOT NULL," +
					   "NAZIONE VARCHAR(20) NOT NULL," +
					   "PROVINCIA VARCHAR(20) NOT NULL," +
					   "CITTA VARCHAR(20) NOT NULL," +
					   "VIA VARCHAR(50) NOT NULL," +
					   "NUMROCIVICO VARCHAR(5) NOT NULL," +
					   "CAP CHAR(5) NOT NULL);";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE STRUTTURA;";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void insert(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
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
																							 ") VALUES (?,?,?,?,?,?,?,?,?,?);");
		
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
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public void update(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
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
																						"WHERE PARTITAIVA = ?;");
		
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
		DB2FactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM STRUTTURA WHERE PARTITAIVA = ?;");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		
		prepStatement.executeUpdate();
		DB2FactoryDAO.closeConnection();
	}

	@Override
	public Struttura get(String partitaIva) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA WHERE PARTITAIVA = ?;");
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.isLast()) throw new SQLException("Not unique identifier: multiple response");
		
		Struttura result = new Struttura();
		
		char[] cap = resS.getString("CAP").toCharArray();
		
		result.setPartitaIva(partitaIva);
		result.setNomeStruttura(resS.getString("NOME"));
		result.setFotoFatturaValida(new File(resS.getString("COGNOME")));
		result.setIban(resS.getString("IBAN"));
		result.setNazione(resS.getString("NAZIONE"));
		result.setProvincia(resS.getString("PROVINCIA"));
		result.setCitta(resS.getString("CITTA"));
		result.setVia(resS.getString("VIA"));
		result.setNumeroCivico(resS.getString("NUMEROCIVICO"));
		result.setCap(cap);
		
		return result;
	}
}
