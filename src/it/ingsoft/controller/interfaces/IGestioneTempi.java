package it.ingsoft.controller.interfaces;

import it.ingsoft.model.tempo.Tempo;

public interface IGestioneTempi {
	public void connettiServizio();
	public void addFineTurnoHandler(FineTurnoHandler fineTurnoHandler);
	public Tempo getNuovoTempo();
}
