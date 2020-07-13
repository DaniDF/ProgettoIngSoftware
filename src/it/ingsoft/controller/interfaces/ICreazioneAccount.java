package it.ingsoft.controller.interfaces;

import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.utente.Utente;

public interface ICreazioneAccount {
	public Struttura creaAccountStruttura(String nazione,
										  String provincia,
										  String citta,
										  String via,
										  String numeroCivico,
										  String cap,
										  String nomeStruttura,
										  String fatturaValida,
										  String partitaIva,
										  String iban,
										  String username,
										  String password);
	
	public Utente creaAccountUtente(String nazione,
									String provincia,
									String citta,
									String via,
									String numeroCivico,
									String cap,
									String nome,
									String cognome,
									String dataDiNascita,
									String luogoDiNascita,
									String codiceFiscale,
									String numeroDiTelefono,
									String username,
									String password);
}
