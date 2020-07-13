package it.ingsoft.controller.struttura;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import it.ingsoft.controller.interfaces.IGestioneStruttura;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.turno.Turno;

public class StrutturaController implements IGestioneStruttura {

	private Struttura struttura;
	
	public StrutturaController(Struttura struttura) {
		this.struttura = struttura;
	}
	
	@Override
	public void addTurni(List<Turno> turni) {
		this.struttura.setTurni(turni);
	}

	@Override
	public void modificaTurno(Turno turno, String daModificare, String value) {
		if(daModificare.equals("inizio"))
		{
			LocalDateTime inizio = null;
			try { LocalDateTime.parse(value); }
			catch (Exception e) { throw new IllegalArgumentException("inizio non valido"); }
			turno.setInizio(inizio);
		}
		else if(daModificare.equals("fine"))
		{
			LocalDateTime fine = null;
			try { LocalDateTime.parse(value); }
			catch (Exception e) { throw new IllegalArgumentException("fine non valida"); }
			turno.setFine(fine);
		}
		else if(daModificare.equals("postiDisponibili"))
		{
			int postiDisponibili = -1;
			try {
				postiDisponibili = Integer.parseInt(value);
				if(postiDisponibili < 0) throw new IllegalArgumentException("postiDisponibili non valido");
			}
			catch (Exception e) { throw new IllegalArgumentException("postiDisponibili non valida"); }
			turno.setPostiDisponibili(postiDisponibili);
		}
		else if(daModificare.equals("prezzo"))
		{
			float prezzo = -1;
			try {
				prezzo = Float.parseFloat(value);
				if(prezzo < 0) throw new IllegalArgumentException("prezzo non valido");
			}
			catch (Exception e) { throw new IllegalArgumentException("prezzo non valido"); }
			turno.setPrezzo(prezzo);
		}
		else throw new IllegalArgumentException("Argomento da modificare inesistente");
	}

	@Override
	public void eliminaTurno(Turno turno) {
		List<Turno> turni = this.struttura.getTurni();
		turni.remove(turno);
		this.struttura.setTurni(turni);
	}

	@Override
	public void modificaDatiStruttura(String daModificare, String value) {
		if(daModificare.equals("nazione"))
		{
			this.struttura.setNazione(value);
		}
		else if(daModificare.equals("provincia"))
		{
			this.struttura.setProvincia(value);
		}
		else if(daModificare.equals("citta"))
		{
			this.struttura.setCitta(value);
		}
		else if(daModificare.equals("via"))
		{
			this.struttura.setVia(value);
		}
		else if(daModificare.equals("numeroCivico"))
		{
			this.struttura.setNumeroCivico(value);
		}
		else if(daModificare.equals("cap"))
		{
			if(value.length() != 5) throw new IllegalArgumentException("cap non valida");
			this.struttura.setCap(value.toCharArray());
		}
		else if(daModificare.equals("nomeStruttura"))
		{
			this.struttura.setNomeStruttura(value);
		}
		else if(daModificare.equals("fatturaValida"))
		{
			File file = null;
			try { file = new File(value); }
			catch (Exception e) { throw new IllegalArgumentException("fatturaValida non valida"); }
			
			this.struttura.setFotoFatturaValida(file);
		}
		else if(daModificare.equals("partitaIva"))
		{
			this.struttura.setPartitaIva(value);
		}
		else if(daModificare.equals("iban"))
		{
			this.struttura.setIban(value);
		}
		else throw new IllegalArgumentException("Argomento da modificare inesistente");
	}
	
}
