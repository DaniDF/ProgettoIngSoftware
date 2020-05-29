package it.ingsoft.model.struttura;

import java.io.File;
import java.util.List;

import it.ingsoft.model.turno.Turno;

public class Struttura {
	private String nomeStruttura;
	private File fotoFatturaValida;
	private String partitaIva;
	private String iban;
	private String nazione;
	private String provincia;
	private String citta;
	private String via;
	private String numeroCivico;
	private char[] cap;
	private List<Turno> turni;
	
	public String getNomeStruttura() {
		return nomeStruttura;
	}
	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}
	public File getFotoFatturaValida() {
		return fotoFatturaValida;
	}
	public void setFotoFatturaValida(File fotoFatturaValida) {
		this.fotoFatturaValida = fotoFatturaValida;
	}
	public String getPartitaIva() {
		return partitaIva;
	}
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	public String getIban() {
		return iban;
	}
	public void setIban(String iban) {
		this.iban = iban;
	}
	public String getNazione() {
		return nazione;
	}
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	public String getProvincia() {
		return provincia;
	}
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public String getVia() {
		return via;
	}
	public void setVia(String via) {
		this.via = via;
	}
	public String getNumeroCivico() {
		return numeroCivico;
	}
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	public char[] getCap() {
		return cap;
	}
	public void setCap(char[] cap) {
		this.cap = cap;
	}
	public List<Turno> getTurni() {
		return turni;
	}
	public void setTurni(List<Turno> turni) {
		this.turni = turni;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		boolean isEq = this.getClass().equals(obj.getClass());
		Struttura objS = null;
		if(isEq) objS = (Struttura)obj;
		
		isEq =  isEq && this.nomeStruttura.equals(objS.nomeStruttura) &&
					    this.fotoFatturaValida.equals(objS.fotoFatturaValida) &&
					    this.partitaIva.equals(objS.partitaIva) &&
					    this.iban.equals(objS.iban) &&
					    this.nazione.equals(objS.nazione) &&
					    this.provincia.equals(objS.provincia) &&
					    this.citta.equals(objS.citta) &&
					    this.via.equals(objS.via) &&
					    this.numeroCivico.equals(objS.numeroCivico) &&
					    this.cap.length == objS.cap.length &&
					    this.turni.size() == objS.turni.size();
		
		for(int cont = 0; !isEq && cont < this.cap.length; cont++)
		{
			isEq = this.cap[cont] == objS.cap[cont];
		}
		
		for(int cont = 0; !isEq && cont < this.turni.size(); cont++)
		{
			isEq = this.turni.get(cont) == objS.turni.get(cont);
		}
		
		return isEq;
	}
}
