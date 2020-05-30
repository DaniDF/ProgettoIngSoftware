package it.ingsoft.model.tempo;

import it.ingsoft.model.utente.Utente;

public class Tempo {
	private int idTempo;
	private Utente utente;
	private long valore;
	
	public int getIdTempo() {
		return idTempo;
	}
	public void setIdTempo(int idTempo) {	//TODO trovare un metodo di identificazione valido
		this.idTempo = idTempo;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public long getValore() {
		return valore;
	}
	public void setValore(long valore) {
		this.valore = valore;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean isEq = this.getClass().equals(obj.getClass());
		Tempo objT = null;
		if(isEq) objT = (Tempo)obj;
		
		isEq =  isEq && this.utente.equals(objT.utente) &&
						this.valore == objT.valore;
		
		return isEq;
	}
}
