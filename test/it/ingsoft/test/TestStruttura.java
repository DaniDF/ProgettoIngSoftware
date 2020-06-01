package it.ingsoft.test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;

public class TestStruttura {
	private Struttura strMis;
	private Struttura strMis2;
	private Struttura strMug;
	
	@Before
	public void init() {
		this.strMis = new Struttura();
		this.strMis.setNomeStruttura("misano");
		this.strMis.setFotoFatturaValida(new File("configDB"));
		this.strMis.setPartitaIva("1234567890");
		this.strMis.setIban("MMMMMMM");
		this.strMis.setNazione("Italia");
		this.strMis.setProvincia("Pesaro Urbino");
		this.strMis.setCitta("Pesaro");
		this.strMis.setVia("via Tavullia");
		this.strMis.setNumeroCivico("46");
		this.strMis.setCap("46464".toCharArray());
		
		this.strMis2 = new Struttura();
		this.strMis2.setNomeStruttura("misano");
		this.strMis2.setFotoFatturaValida(new File("configDB"));
		this.strMis2.setPartitaIva("1234567890");
		this.strMis2.setIban("MMMMMMM");
		this.strMis2.setNazione("Italia");
		this.strMis2.setProvincia("Pesaro Urbino");
		this.strMis2.setCitta("Pesaro");
		this.strMis2.setVia("via Tavullia");
		this.strMis2.setNumeroCivico("46");
		this.strMis2.setCap("46464".toCharArray());
		
		this.strMug = new Struttura();
		this.strMug.setNomeStruttura("mugello");
		this.strMug.setFotoFatturaValida(new File("configDB"));
		this.strMug.setPartitaIva("88659887609");
		this.strMug.setIban("KKKKKKKK");
		this.strMug.setNazione("Italia");
		this.strMug.setProvincia("Pesaro Urbino");
		this.strMug.setCitta("Pesaro");
		this.strMug.setVia("via Tavullia");
		this.strMug.setNumeroCivico("46");
		this.strMug.setCap("46464".toCharArray());
	}
	
	@Test
	public void testEquals()
	{
		assertEquals("misano", this.strMis.getNomeStruttura());
		assertEquals("configDB", this.strMis.getFotoFatturaValida().getName());
		assertEquals("1234567890", this.strMis.getPartitaIva());
		assertEquals("MMMMMMM", this.strMis.getIban());
		assertEquals("Italia", this.strMis.getNazione());
		assertEquals("Pesaro", this.strMis.getCitta());
		assertEquals("via Tavullia", this.strMis.getVia());
		assertEquals("46", this.strMis.getNumeroCivico());
		assertEquals(this.strMis, this.strMis);
		assertNotSame(this.strMis, this.strMug);
	}
	
	@Test
	public void testDB()
	{
		try {
			StrutturaDAO utenteDAO = FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaDAO();
			
			utenteDAO.createTable();
			utenteDAO.insert(this.strMis);
			Struttura temp = utenteDAO.get(this.strMis.getPartitaIva());
			
			assertEquals(this.strMis,temp);
			
			utenteDAO.update(strMis);
			temp = utenteDAO.get(this.strMis.getPartitaIva());
			
			assertEquals(this.strMis,temp);
			
			utenteDAO.delete(strMis);
			
			assertThrows(SQLException.class, () -> utenteDAO.get(this.strMis.getPartitaIva()));
			
			utenteDAO.dropTable();
			
			assertThrows(SQLException.class, () -> utenteDAO.dropTable());
			
		} catch (SQLException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
