package it.ingsoft.persistence.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;

public class MySQLUtenteDAO implements UtenteDAO {

	@Override
	public void createTable() throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		
		String query = "CREATE TABLE UTENTE (" +
					   "CODICEFISCALE VARCHAR(20) NOT NULL PRIMARY KEY," +
					   "NOME VARCHAR(20) NOT NULL," +
					   "COGNOME VARCHAR(20) NOT NULL," +
					   "DATADINASCITA DATE NOT NULL," +
					   "LUOGODINASCITA VARCHAR(20) NOT NULL," +
					   "NUMERODITELEFONO VARCHAR(20) NOT NULL," +
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
		
		String query = "DROP TABLE UTENTE";
		
		Statement statement = connection.createStatement();
		statement.executeUpdate(query);
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void insert(Utente utente) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("INSERT INTO UTENTE (CODICEFISCALE, " +
																						  "NOME, " +
																						  "COGNOME, " +
																						  "DATADINASCITA, " +
																						  "LUOGODINASCITA, " +
																						  "NUMERODITELEFONO, " +
																						  "NAZIONE, " +
																						  "PROVINCIA, " +
																						  "CITTA, " +
																						  "VIA, " +
																						  "NUMEROCIVICO, " +
																						  "CAP" +
																						  ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
		
		Date dataDiNascita = Date.valueOf(utente.getDataDiNascita());
		String cap = String.copyValueOf(utente.getCap());
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		prepStatement.setString(2, utente.getNome());
		prepStatement.setString(3, utente.getCognome());
		prepStatement.setDate(4, dataDiNascita);
		prepStatement.setString(5, utente.getLuogoDiNascita());
		prepStatement.setString(6, utente.getNumeroDiTelefono());
		prepStatement.setString(7, utente.getNazione());
		prepStatement.setString(8, utente.getProvincia());
		prepStatement.setString(9, utente.getCitta());
		prepStatement.setString(10, utente.getVia());
		prepStatement.setString(11, utente.getNumeroCivico());
		prepStatement.setString(12, cap);
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public void update(Utente utente) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("UPDATE UTENTE SET " +
																						"NOME = ?, " +
																						"COGNOME = ?, " +
																						"DATADINASCITA = ?, " +
																						"LUOGODINASCITA = ?, " +
																						"NUMERODITELEFONO = ?, " +
																						"NAZIONE = ?, " +
																						"PROVINCIA = ?, " +
																						"CITTA = ?, " +
																						"VIA = ?, " +
																						"NUMEROCIVICO = ?, " +
																						"CAP = ?" +
																						"WHERE CODICEFISCALE = ?");
		
		Date dataDiNascita = Date.valueOf(utente.getDataDiNascita());
		String cap = String.copyValueOf(utente.getCap());
		
		
		prepStatement.setString(1, utente.getNome());
		prepStatement.setString(2, utente.getCognome());
		prepStatement.setDate(3, dataDiNascita);
		prepStatement.setString(4, utente.getLuogoDiNascita());
		prepStatement.setString(5, utente.getNumeroDiTelefono());
		prepStatement.setString(6, utente.getNazione());
		prepStatement.setString(7, utente.getProvincia());
		prepStatement.setString(8, utente.getCitta());
		prepStatement.setString(9, utente.getVia());
		prepStatement.setString(10, utente.getNumeroCivico());
		prepStatement.setString(11, cap);
		prepStatement.setString(12, utente.getCodiceFiscale());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
		
	}

	@Override
	public void delete(Utente utente) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("DELETE FROM UTENTE WHERE CODICEFISCALE = ?");
		
		prepStatement.setString(1, utente.getCodiceFiscale());
		
		prepStatement.executeUpdate();
		MySQLFactoryDAO.closeConnection();
	}

	@Override
	public Utente get(String codiceFiscale) throws SQLException {
		Connection connection = MySQLFactoryDAO.createConnection();
		PreparedStatement prepStatement = connection.prepareStatement("SELECT * FROM UTENTE WHERE CODICEFISCALE = ?");
		
		prepStatement.setString(1, codiceFiscale);
		
		ResultSet resS = prepStatement.executeQuery();
		if(!resS.next()) throw new SQLException("No result");
		
		Utente result = new Utente();
		
		LocalDate dataDiNascita = resS.getDate("DATADINASCITA").toLocalDate();
		char[] cap = resS.getString("CAP").toCharArray();
		
		result.setCodiceFiscale(codiceFiscale);
		result.setNome(resS.getString("NOME"));
		result.setCognome(resS.getString("COGNOME"));
		result.setDataDiNascita(dataDiNascita);
		result.setNumeroDiTelefono(resS.getString("NUMERODITELEFONO"));
		result.setLuogoDiNascita(resS.getString("LUOGODINASCITA"));
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
