package it.ingsoft.model.turno;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.tempo.Tempo;

public class Turno {
	private int id;
	private Struttura struttura; //TODO non c'era nel modello
	private LocalDateTime inizio;
	private LocalDateTime fine;
	private int postiDisponibili;
	private float prezzo;
	private List<Tempo> tempi;
	
	public Turno()
	{
		this.tempi = new ArrayList<>();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {	//TODO trovare un metodo di identificazione valido
		this.id = id;
	}
	public Struttura getStruttura() {
		return struttura;
	}
	public void setStruttura(Struttura struttura) {
		this.struttura = struttura;
	}
	public LocalDateTime getInizio() {
		return inizio;
	}
	public void setInizio(LocalDateTime inizio) {
		this.inizio = inizio;
	}
	public LocalDateTime getFine() {
		return fine;
	}
	public void setFine(LocalDateTime fine) {
		this.fine = fine;
	}
	public int getPostiDisponibili() {
		return postiDisponibili;
	}
	public void setPostiDisponibili(int postiDisponibili) {
		this.postiDisponibili = postiDisponibili;
	}
	public float getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}
	public List<Tempo> getTempi() {
		return tempi;
	}
	public void setTempi(List<Tempo> tempi) {
		this.tempi = tempi;
	}
	
	public boolean isFinito()
	{
		return LocalDateTime.now().isAfter(fine);
	}
	
	public void addTempo(Tempo tempo)
	{
		this.tempi.add(tempo);
	}
	
	public void prenota()
	{
		this.postiDisponibili -= 1;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean isEq = this.getClass().equals(obj.getClass());
		Turno objT = null;
		if(isEq) objT = (Turno)obj;
		
		isEq =  isEq && this.id == objT.id &&
					    this.inizio.equals(objT.inizio) &&
					    this.fine.equals(objT.fine) &&
					    this.postiDisponibili == objT.postiDisponibili &&
					    this.prezzo == objT.prezzo &&
					    this.tempi.size() == objT.tempi.size();
		
		for(int cont = 0; isEq && cont < this.tempi.size(); cont++)
		{
			isEq = this.tempi.get(cont).equals(objT.tempi.get(cont));
		}
		
		return isEq;
	}
}
