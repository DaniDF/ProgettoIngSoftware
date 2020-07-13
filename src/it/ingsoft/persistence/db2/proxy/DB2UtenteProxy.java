package it.ingsoft.persistence.db2.proxy;

import java.sql.SQLException;
import java.time.LocalDate;

import it.ingsoft.model.utente.Utente;
import it.ingsoft.model.utente.UtenteDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;
import it.ingsoft.persistence.db2.DB2FactoryDAO;

public class DB2UtenteProxy extends Utente {
	
	static
	{
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			UtenteDAO utenteDAO = factory.getUtenteDAO();
			utenteDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella utenti");
		}
	}
	
	public DB2UtenteProxy(String codiceFiscale) {
		super();
		
		try
		{
			UtenteDAO utenteDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteDAO();
			Utente newUtente = utenteDAO.get(codiceFiscale);
			
			super.setCodiceFiscale(codiceFiscale);
			super.setCap(newUtente.getCap());
			super.setCitta(newUtente.getCitta());
			super.setCognome(newUtente.getCognome());
			super.setDataDiNascita(newUtente.getDataDiNascita());
			super.setLuogoDiNascita(newUtente.getLuogoDiNascita());
			super.setNazione(newUtente.getNazione());
			super.setNome(newUtente.getNome());
			super.setNumeroCivico(newUtente.getNumeroCivico());
			super.setNumeroDiTelefono(newUtente.getNumeroDiTelefono());
			super.setProvincia(newUtente.getProvincia());
			super.setVia(newUtente.getVia());
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare l'utente: " + e.getMessage());
			
			super.setCodiceFiscale(codiceFiscale);
			
			try
			{
				UtenteDAO utenteDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteDAO();
				utenteDAO.insert(this);
				
			} catch(SQLException ee) {
				System.err.println("Impossibile salvare l'utente: " + ee.getMessage());
			}
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
	public void setCodiceFiscale(String codiceFiscale) {
		throw new InvalidUseException("Metodo non utilizzabile per la sincronia remota");
	}
	
	@Override
	public void setCognome(String cognome) {
		super.setCognome(cognome);
		this.update();
	}
	
	@Override
	public void setDataDiNascita(LocalDate dataDiNascita) {
		super.setDataDiNascita(dataDiNascita);
		this.update();
	}
	
	@Override
	public void setLuogoDiNascita(String luogoDiNascita) {
		super.setLuogoDiNascita(luogoDiNascita);
		this.update();
	}
	
	@Override
	public void setNazione(String nazione) {
		super.setNazione(nazione);
		this.update();
	}
	
	@Override
	public void setNome(String nome) {
		super.setNome(nome);
		this.update();
	}
	
	@Override
	public void setNumeroCivico(String numeroCivico) {
		super.setNumeroCivico(numeroCivico);
		this.update();
	}
	
	@Override
	public void setNumeroDiTelefono(String numeroDiTelefono) {
		super.setNumeroDiTelefono(numeroDiTelefono);
		this.update();
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
			UtenteDAO utenteDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getUtenteDAO();
			utenteDAO.update(this);
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare l'utente: " + e.getMessage());
		}
	}
}
