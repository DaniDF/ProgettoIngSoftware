package it.ingsoft.persistence.db2.proxy;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;
import it.ingsoft.persistence.db2.DB2FactoryDAO;

public class DB2StrutturaProxy extends Struttura {
	
	static
	{
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			StrutturaDAO strutturaDAO = factory.getStrutturaDAO();
			strutturaDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella struttura");
		}
	}
	
	public DB2StrutturaProxy(String partitaIva) {
		super();
		
		try
		{
			StrutturaDAO strutturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaDAO();
			Struttura newStruttura = strutturaDAO.get(partitaIva);
			
			super.setPartitaIva(partitaIva);
			super.setCap(newStruttura.getCap());
			super.setCitta(newStruttura.getCitta());
			super.setFotoFatturaValida(newStruttura.getFotoFatturaValida());
			super.setIban(newStruttura.getIban());
			super.setNazione(newStruttura.getNazione());
			super.setNomeStruttura(newStruttura.getNomeStruttura());
			super.setNumeroCivico(newStruttura.getNumeroCivico());
			super.setProvincia(newStruttura.getProvincia());
			super.setVia(newStruttura.getVia());
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare la struttura: " + e.getMessage());
			
			super.setPartitaIva(partitaIva);
			
			try
			{
				StrutturaDAO strutturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaDAO();
				strutturaDAO.insert(this);
				
			} catch(SQLException ee) {
				System.err.println("Impossibile salvare la struttura: " + ee.getMessage());
			}
		}
	}
	
	@Override
	public List<Turno> getTurni() {
		List<Turno> result = null;
		
		try
		{
			StrutturaTurnoMappingDAO strTurDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaTurnoMappingDAO();
			result = strTurDAO.getTurni(this);
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i turni: " + e.getMessage());
			result = new ArrayList<>();
		}
		
		return result;
	}
	
	@Override
	public void setTurni(List<Turno> turni) {
		super.setTurni(turni);
		
		try
		{
			StrutturaTurnoMappingDAO strTurDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaTurnoMappingDAO();
			
			for(Turno t : turni)
			{	
				strTurDAO.insert(this, t);
			}
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i turni: " + e.getMessage());
		}
	}
	
	@Override
	public void setCap(char[] cap) {
		super.setCap(cap);
		this.update();
	}
	
	@Override
	public void setCitta(String citta) {
		super.setCitta(citta);
		this.update();
	}
	
	@Override
	public void setFotoFatturaValida(File fotoFatturaValida) {
		super.setFotoFatturaValida(fotoFatturaValida);
		this.update();
	}
	
	@Override
	public void setIban(String iban) {
		super.setIban(iban);
		this.update();
	}
	
	@Override
	public void setNazione(String nazione) {
		super.setNazione(nazione);
		this.update();
	}
	
	@Override
	public void setNomeStruttura(String nomeStruttura) {
		super.setNomeStruttura(nomeStruttura);
		this.update();
	}
	
	@Override
	public void setNumeroCivico(String numeroCivico) {
		super.setNumeroCivico(numeroCivico);
		this.update();
	}
	
	@Override
	public void setPartitaIva(String partitaIva) {
		throw new InvalidUseException("Metodo non utilizzabile per la sincronia remota");
	}
	
	@Override
	public void setProvincia(String provincia) {
		super.setProvincia(provincia);
		this.update();
	}
	
	@Override
	public void setVia(String via) {
		super.setVia(via);
		this.update();
	}
	
	private void update()
	{
		try {
			StrutturaDAO strutturaDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaDAO();
			strutturaDAO.update(this);
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare la struttura: " + e.getMessage());
		}
	}
}
