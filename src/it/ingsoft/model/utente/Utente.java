package it.ingsoft.model.utente;

import java.time.LocalDate;

public class Utente {
	private String nome;
	private String cognome;
	private LocalDate dataDiNascita;
	private String luogoDiNascita;
	private String codiceFiscale;
	private String numeroDiTelefono;
	private String nazione;
	private String provincia;
	private String citta;
	private String via;
	private String numeroCivico;
	private char[] cap;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}
	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getNumeroDiTelefono() {
		return numeroDiTelefono;
	}
	public void setNumeroDiTelefono(String numeroDiTelefono) {
		this.numeroDiTelefono = numeroDiTelefono;
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
	
	@Override
	public boolean equals(Object obj)
	{
		boolean isEq = this.getClass().equals(obj.getClass());
		Utente objU = null;
		if(isEq) objU = (Utente)obj;
		
		isEq =  isEq && this.nome.equals(objU.nome) &&
					    this.cognome.equals(objU.cognome) &&
					    this.dataDiNascita.equals(objU.dataDiNascita) &&
					    this.codiceFiscale.equals(objU.codiceFiscale) &&
					    this.numeroDiTelefono.equals(objU.numeroDiTelefono) &&
					    this.nazione.equals(objU.nazione) &&
					    this.provincia.equals(objU.provincia) &&
					    this.citta.equals(objU.citta) &&
					    this.via.equals(objU.via) &&
					    this.numeroCivico.equals(objU.numeroCivico) &&
					    this.cap.length == objU.cap.length;
		
		for(int cont = 0; !isEq && cont < this.cap.length; cont++)
		{
			isEq = this.cap[cont] == objU.cap[cont];
		}
		
		return isEq;
	}
}
