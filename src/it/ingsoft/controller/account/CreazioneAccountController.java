package it.ingsoft.controller.account;

import java.io.File;
import java.time.LocalDate;

import it.ingsoft.controller.interfaces.ICreazioneAccount;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.utente.Utente;
import it.ingsoft.persistence.db2.proxy.DB2StrutturaProxy;
import it.ingsoft.persistence.db2.proxy.DB2UtenteProxy;

public class CreazioneAccountController implements ICreazioneAccount {

	@Override
	public Struttura creaAccountStruttura(String nazione, String provincia, String citta, String via,
			String numeroCivico, String cap, String nomeStruttura, String fatturaValida, String partitaIva, String iban,
			String username, String password) {
		
		if(cap.length() != 5) throw new IllegalArgumentException("CAP not valid");
		
		Struttura result = new DB2StrutturaProxy(partitaIva);
		
		result.setNazione(nazione);
		result.setProvincia(provincia);
		result.setCitta(citta);
		result.setVia(via);
		result.setNumeroCivico(numeroCivico);
		result.setCap(cap.toCharArray());
		result.setNomeStruttura(nomeStruttura);
		result.setFotoFatturaValida(new File(fatturaValida));
		result.setIban(iban);
		
		return result;
	}

	@Override
	public Utente creaAccountUtente(String nazione, String provincia, String citta, String via, String numeroCivico,
			String cap, String nome, String cognome, String dataDiNascita, String luogoDiNascita, String codiceFiscale,
			String numeroDiTelefono, String username, String password) {

		LocalDate date = null;
		try { date = LocalDate.parse(dataDiNascita); }
		catch (Exception e) { throw new IllegalArgumentException("dataDiNascita not valid"); }
		if(cap.length() != 5) throw new IllegalArgumentException("CAP not valid");
		
		Utente result = new DB2UtenteProxy(codiceFiscale);
		
		result.setNazione(nazione);
		result.setProvincia(provincia);
		result.setCitta(citta);
		result.setVia(via);
		result.setNumeroCivico(numeroCivico);
		result.setCap(cap.toCharArray());
		result.setNome(nome);
		result.setCognome(cognome);
		result.setDataDiNascita(date);
		result.setLuogoDiNascita(luogoDiNascita);
		result.setNumeroDiTelefono(numeroDiTelefono);
		
		return result;
	}
}
