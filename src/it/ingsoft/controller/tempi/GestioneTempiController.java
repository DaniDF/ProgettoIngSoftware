package it.ingsoft.controller.tempi;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import it.ingsoft.controller.interfaces.FineTurnoHandler;
import it.ingsoft.controller.interfaces.IGestioneTempi;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.db2.proxy.DB2TempoProxy;

public class GestioneTempiController implements IGestioneTempi {

	private Utente utente;
	private Set<FineTurnoHandler> handlers;
	
	public GestioneTempiController(Utente utente) {
		this.utente = utente;
		this.handlers = new HashSet<>();
	}
	
	@Override
	public void connettiServizio() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFineTurnoHandler(FineTurnoHandler fineTurnoHandler) {
		this.handlers.add(fineTurnoHandler);
	}

	@Override
	public Tempo getNuovoTempo() {
		
		Tempo result = new DB2TempoProxy((int)(System.currentTimeMillis()/1000));
		result.setValore(new Random().nextLong());
		
		result.setUtente(this.utente);
		
		return result;
	}

}
