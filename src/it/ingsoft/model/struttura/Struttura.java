package it.ingsoft.model.struttura;

import java.io.File;
import java.util.ArrayList;
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
	
	public Struttura()
	{
		this.turni = new ArrayList<>();
	}
	
	/**
	 * Metodo per acquisire il nome della struttura
	 * 
	 * @return	Il nome della struttura
	 */
	public String getNomeStruttura() {
		return nomeStruttura;
	}
	
	/**
	 * Metodo per settare il nome della struttura
	 * 
	 * @param nome Nome della struttura
	 */
	public void setNomeStruttura(String nomeStruttura) {
		this.nomeStruttura = nomeStruttura;
	}
	
	/**
	 * Metodo per acquisire il file di conferma della struttura
	 * 
	 * @return	Il file di conferma della struttura
	 */
	public File getFotoFatturaValida() {
		return fotoFatturaValida;
	}
	
	/**
	 * Metodo per settare il file di conferma della struttura
	 * 
	 * @param nome File di conferma della struttura
	 */
	public void setFotoFatturaValida(File fotoFatturaValida) {
		this.fotoFatturaValida = fotoFatturaValida;
	}
	
	/**
	 * Metodo per acquisire la partita iva della struttura
	 * 
	 * @return	La partita della struttura
	 */
	public String getPartitaIva() {
		return partitaIva;
	}
	
	/**
	 * Metodo per settare la partita iva della struttura
	 * 
	 * @param nome La partita iva della struttura
	 */
	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}
	
	/**
	 * Metodo per acquisire l'iban della struttura
	 * 
	 * @return	L'iban della struttura
	 */
	public String getIban() {
		return iban;
	}
	
	/**
	 * Metodo per settare l'iban della struttura
	 * 
	 * @param nome L'iban della struttura
	 */
	public void setIban(String iban) {
		this.iban = iban;
	}
	
	/**
	 * Metodo per acquisire la nazione della struttura
	 * 
	 * @return	L'iban della struttura
	 */
	public String getNazione() {
		return nazione;
	}
	
	/**
	 * Metodo per settare la nazione della struttura
	 * 
	 * @param dataDiNascita Nazione della struttura
	 */
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	
	/**
	 * Metodo per acquisire la provincia della struttura
	 * 
	 * @return La provincia della struttura
	 */
	public String getProvincia() {
		return provincia;
	}
	
	/**
	 * Metodo per settare la provincia della struttura
	 * 
	 * @param dataDiNascita Provincia della struttura
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	/**
	 * Metodo per acquisire la citta' della struttura
	 * 
	 * @return La citta' della struttura
	 */
	public String getCitta() {
		return citta;
	}
	
	/**
	 * Metodo per settare la citta' della struttura
	 * 
	 * @param dataDiNascita Citta' della struttura
	 */
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	/**
	 * Metodo per acquisire la via della struttura
	 * 
	 * @return La via della struttura
	 */
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
		
		for(int cont = 0; isEq && cont < this.turni.size(); cont++)
		{
			isEq = this.turni.get(cont).equals(objS.turni.get(cont));
		}
		
		return isEq;
	}
}
