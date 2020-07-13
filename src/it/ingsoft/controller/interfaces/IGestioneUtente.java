package it.ingsoft.controller.interfaces;

import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.metodoPagamento.MetodoPagamento;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.turno.Turno;

public interface IGestioneUtente {
	public List<Struttura> getStrutture();
	public List<Turno> getTurni(Struttura struttura);
	public List<Tempo> getTempi(Turno turno);
	public Statistica getStatistiche(Turno turno);
	public void modificaDatiUtenti(String daModificare, String value);
	public Fattura acquistaCarrello(MetodoPagamento metodoPagamento);
	public void addCarrello(List<Turno> turni);
}
