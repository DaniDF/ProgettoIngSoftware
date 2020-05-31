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
	
	/**
	 * Metodo per acquisire il nome dell'utente
	 * 
	 * @return	Il nome dell'utente
	 */
	public String getNome() {
		return nome;
	}
	
	/**
	 * Metodo per settare il nome dell'utente
	 * 
	 * @param nome Nome dell'utente
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	/**
	 * Metodo per acquisire il cognome dell'utente
	 * 
	 * @return	Il cognome dell'utente
	 */
	public String getCognome() {
		return cognome;
	}
	
	/**
	 * Metodo per settare il cognome dell'utente
	 * 
	 * @param nome Cognome dell'utente
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	/**
	 * Metodo per acquisire la data di nascita dell'utente
	 * 
	 * @return La data di nascita dell'utente
	 */
	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	
	/**
	 * Metodo per settare la data di nascita dell'utente
	 * 
	 * @param dataDiNascita Data di nascita dell'utente
	 */
	public void setDataDiNascita(LocalDate dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	
	/**
	 * Metodo per acquisire il luogo di nascita dell'utente
	 * 
	 * @return Il luogo di nascita dell'utente
	 */
	public String getLuogoDiNascita() {
		return luogoDiNascita;
	}
	
	/**
	 * Metodo per settare il luogo di nascita dell'utente
	 * 
	 * @param dataDiNascita Luogo di nascita dell'utente
	 */
	public void setLuogoDiNascita(String luogoDiNascita) {
		this.luogoDiNascita = luogoDiNascita;
	}
	
	/**
	 * Metodo per acquisire il codice fiscale dell'utente
	 * 
	 * @return Il codice fiscale dell'utente
	 */
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	
	/**
	 * Metodo per settare il codice fiscale dell'utente
	 * 
	 * @param dataDiNascita Codice fiscale dell'utente
	 */
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	
	/**
	 * Metodo per acquisire il numero di telefono dell'utente
	 * 
	 * @return Il codice fiscale dell'utente
	 */
	public String getNumeroDiTelefono() {
		return numeroDiTelefono;
	}
	
	/**
	 * Metodo per settare il numero di telefono dell'utente
	 * 
	 * @param dataDiNascita Numero di telefono dell'utente
	 */
	public void setNumeroDiTelefono(String numeroDiTelefono) {
		this.numeroDiTelefono = numeroDiTelefono;
	}
	
	/**
	 * Metodo per acquisire la nazione dell'utente
	 * 
	 * @return La nazione dell'utente
	 */
	public String getNazione() {
		return nazione;
	}
	
	/**
	 * Metodo per settare la nazione dell'utente
	 * 
	 * @param dataDiNascita Nazione dell'utente
	 */
	public void setNazione(String nazione) {
		this.nazione = nazione;
	}
	
	/**
	 * Metodo per acquisire la provincia dell'utente
	 * 
	 * @return La provincia dell'utente
	 */
	public String getProvincia() {
		return provincia;
	}
	
	/**
	 * Metodo per settare la provincia dell'utente
	 * 
	 * @param dataDiNascita Provincia dell'utente
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	/**
	 * Metodo per acquisire la citta' dell'utente
	 * 
	 * @return La citta' dell'utente
	 */
	public String getCitta() {
		return citta;
	}
	
	/**
	 * Metodo per settare la citta' dell'utente
	 * 
	 * @param dataDiNascita Citta' dell'utente
	 */
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	/**
	 * Metodo per acquisire la via dell'utente
	 * 
	 * @return La via dell'utente
	 */
	public String getVia() {
		return via;
	}
	
	/**
	 * Metodo per settare la via dell'utente
	 * 
	 * @param dataDiNascita Via dell'utente
	 */
	public void setVia(String via) {
		this.via = via;
	}
	
	/**
	 * Metodo per acquisire il numero civico dell'utente
	 * 
	 * @return Il numero civico dell'utente
	 */
	public String getNumeroCivico() {
		return numeroCivico;
	}
	
	/**
	 * Metodo per settare il numero civico dell'utente
	 * 
	 * @param dataDiNascita Numero civico dell'utente
	 */
	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	
	/**
	 * Metodo per acquisire il CAP dell'utente
	 * 
	 * @return Il CAP dell'utente
	 */
	public char[] getCap() {
		return cap;
	}
	
	/**
	 * Metodo per settare il numero civico dell'utente
	 * 
	 * @param dataDiNascita Numero civico dell'utente
	 */
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
		
		for(int cont = 0; isEq && cont < this.cap.length; cont++)
		{
			isEq = this.cap[cont] == objU.cap[cont];
		}
		
		return isEq;
	}
}
