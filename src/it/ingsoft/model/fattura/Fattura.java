package it.ingsoft.model.fattura;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.metodoPagamento.MetodoPagamento;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.utente.Utente;

public class Fattura {
	private int idFattura;
	private MetodoPagamento metodoPagamento;
	private Utente utente;
	private List<Turno> acquisti;
	
	public Fattura()
	{
		this.acquisti = new ArrayList<>();
	}
	
	public int getIdFattura() {
		return idFattura;
	}
	public void setIdFattura(int idFattura) {
		this.idFattura = idFattura;
	}
	public MetodoPagamento getMetodoPagamento() {
		return metodoPagamento;
	}
	public void setMetodoPagamento(MetodoPagamento metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}
	public Utente getUtente() {
		return utente;
	}
	public void setUtente(Utente utente) {
		this.utente = utente;
	}
	public List<Turno> getAcquisti() {
		return acquisti;
	}
	public void setAcquisti(List<Turno> acquisti) {
		this.acquisti = acquisti;
	}
	
	public File stampaFattura(String posizione)
	{
		//TODO Generare un file che reppresenti la fattura e restituire il suo riferimento come oggetto file
		return null;
	}
	
	public float getImport()
	{
		float result = 0;
		
		for(Turno t : this.acquisti)
		{
			result += t.getPrezzo();
		}
		
		return result;
	}
	
	public void addAcquisto(Turno turno)
	{
		this.acquisti.add(turno);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean isEq = this.getClass().equals(obj.getClass());
		Fattura objF = null;
		if(isEq) objF = (Fattura)obj;
		
		isEq =  isEq && this.idFattura == objF.idFattura &&
					    //this.metodoPagamento.equals(objF.metodoPagamento) && //TODO Cha facciamo qua'?
					    this.utente.equals(objF.utente) &&
					    this.acquisti.size() == objF.acquisti.size();
		
		for(int cont = 0; isEq && cont < this.acquisti.size(); cont++)
		{
			isEq = this.acquisti.get(cont).equals(objF.acquisti.get(cont));
		}
		
		return isEq;
	}
}
