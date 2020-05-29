package it.ingsoft.model.tempo;

import it.ingsoft.model.utente.Utente;

public class Tempo {
	private Utente utente;
	private long valore;
	
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
