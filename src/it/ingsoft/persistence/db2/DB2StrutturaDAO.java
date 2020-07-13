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
					   "PARTITAIVA VARCHAR(30) NOT NULL PRIMARY KEY," +
					   "NOMESTRUTTURA VARCHAR(20)," +
					   "FOTOFATTURAVALIDA VARCHAR(500)," +
					   "IBAN VARCHAR(30)," +
					   "NAZIONE VARCHAR(20)," +
					   "PROVINCIA VARCHAR(20)," +
					   "CITTA VARCHAR(20)," +
					   "VIA VARCHAR(50)," +
					   "NUMEROCIVICO VARCHAR(5)," +
					   "CAP VARCHAR(5) )";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		statement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void dropTable() throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		
		String query = "DROP TABLE STRUTTURA";
		
		Statement statement = connection.createStatement();
		statement.execute(query);
		statement.close();
		DB2FactoryDAO.closeConnection(connection);
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
																							 ") VALUES (?,?,?,?,?,?,?,?,?,?)");
		
		String cap = null;
		String pathFoto = null;
		try { cap = String.copyValueOf(struttura.getCap()); } catch(NullPointerException e) { cap = null; }
		try { struttura.getFotoFatturaValida().getAbsolutePath(); } catch (NullPointerException e) { pathFoto = null; }
		
		prepStatement.setString(1, struttura.getPartitaIva());
		prepStatement.setString(2, struttura.getNomeStruttura());
		prepStatement.setString(3, pathFoto);
		prepStatement.setString(4, struttura.getIban());
		prepStatement.setString(5, struttura.getNazione());
		prepStatement.setString(6, struttura.getProvincia());
		prepStatement.setString(7, struttura.getCitta());
		prepStatement.setString(8, struttura.getVia());
		prepStatement.setString(9, struttura.getNumeroCivico());
		prepStatement.setString(10, cap);
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
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
																						"WHERE PARTITAIVA = ?");
		
		String cap = null;
		String pathFoto = null;
		try { cap = String.copyValueOf(struttura.getCap()); } catch(NullPointerException e) { cap = null; }
		try { struttura.getFotoFatturaValida().getAbsolutePath(); } catch (NullPointerException e) { pathFoto = null; }
		
		prepStatement.setString(1, struttura.getNomeStruttura());
		prepStatement.setString(2, pathFoto);
		prepStatement.setString(3, struttura.getIban());
		prepStatement.setString(4, struttura.getNazione());
		prepStatement.setString(5, struttura.getProvincia());
		prepStatement.setString(6, struttura.getCitta());
		prepStatement.setString(7, struttura.getVia());
		prepStatement.setString(8, struttura.getNumeroCivico());
		prepStatement.setString(9, cap);
		prepStatement.setString(10, struttura.getPartitaIva());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public void delete(Struttura struttura) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM STRUTTURA WHERE PARTITAIVA = ?");
		
		prepStatement.setString(1, struttura.getPartitaIva());
		
		prepStatement.executeUpdate();

		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
	}

	@Override
	public Struttura get(String partitaIva) throws SQLException {
		Connection connection = DB2FactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM STRUTTURA WHERE PARTITAIVA = ?");
		
		prepStatement.setString(1, partitaIva);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Struttura result = new Struttura();
		
		char[] cap = resS.getString("CAP").toCharArray();
		File file = null;
		if(resS.getString("FOTOFATTURAVALIDA") != null) file = new File(resS.getString("FOTOFATTURAVALIDA"));
		
		result.setPartitaIva(partitaIva);
		result.setNomeStruttura(resS.getString("NOMESTRUTTURA"));
		result.setFotoFatturaValida(file);
		result.setIban(resS.getString("IBAN"));
		result.setNazione(resS.getString("NAZIONE"));
		result.setProvincia(resS.getString("PROVINCIA"));
		result.setCitta(resS.getString("CITTA"));
		result.setVia(resS.getString("VIA"));
		result.setNumeroCivico(resS.getString("NUMEROCIVICO"));
		result.setCap(cap);
		
		if(resS.next()) throw new SQLException("Not unique identifier: multiple response");
		
		prepStatement.close();
		DB2FactoryDAO.closeConnection(connection);
		
		return result;
	}
}
