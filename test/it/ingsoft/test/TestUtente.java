package it.ingsoft.test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class TestUtente {
	private Utente utenteRossi;
	private Utente utenteRossi2;
	private Utente utenteSic;
	
	@Before
	public void init() {
		this.utenteRossi = new Utente();
		this.utenteRossi.setNome("Valentino");
		this.utenteRossi.setCognome("Rossi");
		this.utenteRossi.setCodiceFiscale("RSSVNT79B16G479B");
		this.utenteRossi.setDataDiNascita(LocalDate.of(1979, 2, 16));
		this.utenteRossi.setLuogoDiNascita("Pesaro");
		this.utenteRossi.setNumeroDiTelefono("4646464646");
		this.utenteRossi.setNazione("Italia");
		this.utenteRossi.setProvincia("Pesaro Urbino");
		this.utenteRossi.setCitta("Pesaro");
		this.utenteRossi.setVia("via Tavullia");
		this.utenteRossi.setNumeroCivico("46");
		this.utenteRossi.setCap("46464".toCharArray());
		
		this.utenteRossi2 = new Utente();
		this.utenteRossi2.setNome("Valentino");
		this.utenteRossi2.setCognome("Rossi");
		this.utenteRossi2.setCodiceFiscale("RSSVNT79B16G479B");
		this.utenteRossi2.setDataDiNascita(LocalDate.of(1979, 2, 16));
		this.utenteRossi.setLuogoDiNascita("Pesaro");
		this.utenteRossi2.setNumeroDiTelefono("4646464646");
		this.utenteRossi2.setNazione("Italia");
		this.utenteRossi2.setProvincia("Pesaro Urbino");
		this.utenteRossi2.setCitta("Pesaro");
		this.utenteRossi2.setVia("via Tavullia");
		this.utenteRossi2.setNumeroCivico("46");
		this.utenteRossi2.setCap("46464".toCharArray());
		
		this.utenteSic = new Utente();
		this.utenteSic.setNome("Marco");
		this.utenteSic.setCognome("Simoncelli");
		this.utenteSic.setCodiceFiscale("RSSVNT79B16G479B");
		this.utenteSic.setDataDiNascita(LocalDate.of(1979, 2, 16));
		this.utenteSic.setLuogoDiNascita("Pesaro");
		this.utenteSic.setNumeroDiTelefono("4646464646");
		this.utenteSic.setNazione("Italia");
		this.utenteSic.setProvincia("Pesaro Urbino");
		this.utenteSic.setCitta("Pesaro");
		this.utenteSic.setVia("via Tavullia");
		this.utenteSic.setNumeroCivico("46");
		this.utenteSic.setCap("46464".toCharArray());
	}
	
	@Test
	public void testEquals()
	{
		assertEquals("Valentino", this.utenteRossi.getNome());
		assertEquals("Rossi", this.utenteRossi.getCognome());
		assertEquals(LocalDate.of(1979, 2, 16), this.utenteRossi.getDataDiNascita());
		assertEquals("4646464646", this.utenteRossi.getNumeroDiTelefono());
		assertEquals("Italia", this.utenteRossi.getNazione());
		assertEquals("Pesaro", this.utenteRossi.getCitta());
		assertEquals("via Tavullia", this.utenteRossi.getVia());
		assertEquals("46", this.utenteRossi.getNumeroCivico());
		assertEquals(this.utenteRossi, this.utenteRossi2);
		assertNotSame(this.utenteRossi, this.utenteSic);
	}
	
	@Test
	public void testDB()
	{
		try {
			UtenteDAO utenteDAO = FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteDAO();
			
			utenteDAO.createTable();
			utenteDAO.insert(this.utenteRossi);
			Utente temp = utenteDAO.get(this.utenteRossi.getCodiceFiscale());
			
			assertEquals(this.utenteRossi,temp);
			
			utenteDAO.update(utenteSic);
			temp = utenteDAO.get(this.utenteRossi.getCodiceFiscale());
			
			assertEquals(this.utenteSic,temp);
			
			utenteDAO.delete(utenteSic);
			
			assertThrows(SQLException.class, () -> utenteDAO.get(this.utenteRossi.getCodiceFiscale()));
			
			utenteDAO.dropTable();
			
			assertThrows(SQLException.class, () -> utenteDAO.dropTable());
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
