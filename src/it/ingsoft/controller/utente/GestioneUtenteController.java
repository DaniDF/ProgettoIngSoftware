package it.ingsoft.controller.utente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.controller.interfaces.FineTurnoHandler;
import it.ingsoft.controller.interfaces.IGestioneUtente;
import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.metodoPagamento.MetodoPagamento;
import it.ingsoft.model.security.Credentials;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.struttura.StrutturaDAO;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;
import it.ingsoft.persistence.db2.DB2FatturaDAO;
import it.ingsoft.persistence.db2.proxy.DB2FatturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2StrutturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2TurnoProxy;

public class GestioneUtenteController implements IGestioneUtente, FineTurnoHandler {

	private Utente utente;
	private List<Turno> carrello;
	
	public GestioneUtenteController(Utente utente) {
		this.utente = utente;
		this.carrello = new ArrayList<>();
	}
	
	@Override
	public void makeStatistiche(Turno turno) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Struttura> getStrutture() {
		List<Struttura> result = new ArrayList<>();
		
		try
		{
			FactoryDAO factoryDAO = FactoryDAO.getDAOFactory(DBInstance.DB2);
			StrutturaDAO strutturaDAO = factoryDAO.getStrutturaDAO();
			
			result = strutturaDAO.getAll();
			
		} catch (SQLException e) { }
		
		return result;
	}

	@Override
	public List<Turno> getTurni(Struttura struttura) {
		return new DB2StrutturaProxy(struttura.getPartitaIva()).getTurni();
	}

	@Override
	public List<Tempo> getTempi(Turno turno) {
		return new DB2TurnoProxy(turno.getId()).getTempi();
	}

	@Override
	public void modificaDatiUtenti(String daModificare, String value) {
		if(daModificare.equals("nazione"))
		{
			this.utente.setNazione(value);
		}
		else if(daModificare.equals("provincia"))
		{
			this.utente.setProvincia(value);
		}
		else if(daModificare.equals("citta"))
		{
			this.utente.setCitta(value);
		}
		else if(daModificare.equals("via"))
		{
			this.utente.setVia(value);
		}
		else if(daModificare.equals("numeroCivico"))
		{
			this.utente.setNumeroCivico(value);
		}
		else if(daModificare.equals("cap"))
		{
			if(value.length() != 5) throw new IllegalArgumentException("cap non valida");
			this.utente.setCap(value.toCharArray());
		}
		else if(daModificare.equals("nome"))
		{
			this.utente.setNome(value);
		}
		else if(daModificare.equals("cognome"))
		{
			this.utente.setCognome(value);
		}
		else if(daModificare.equals("dataDiNascita"))
		{
			LocalDate date = null;
			try { date = LocalDate.parse(value); }
			catch(Exception e) { throw new IllegalArgumentException("dataDiNascita non valida",e); }
			
			this.utente.setDataDiNascita(date);
		}
		else if(daModificare.equals("luogoDiNascita"))
		{
			this.utente.setCognome(value);
		}
		else if(daModificare.equals("numeroDiTelefono"))
		{
			this.utente.setCognome(value);
		}
		else throw new IllegalArgumentException("Argomento da modificare inesistente");
	}

	@Override
	public Fattura acquistaCarrello(MetodoPagamento metodoPagamento) {
		Fattura fattura = new DB2FatturaProxy((int)(System.currentTimeMillis()/1000));
		fattura.setAcquisti(this.carrello);
		
		return fattura;
	}

	@Override
	public void addCarrello(List<Turno> turni) {
		this.carrello.addAll(turni);
	}

	@Override
	public Statistica getStatistiche(Turno turno) {
		// TODO Auto-generated method stub
		return null;
	}

}
