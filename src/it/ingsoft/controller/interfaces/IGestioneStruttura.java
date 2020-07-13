package it.ingsoft.controller.interfaces;

import java.util.List;

import it.ingsoft.model.turno.Turno;

public interface IGestioneStruttura {
	public void addTurni(List<Turno> turni);
	public void modificaTurno(Turno turno, String daModificare, String valore);
	public void eliminaTurno(Turno turno);
	public void modificaDatiStruttura(String daModificare, String value);
}
