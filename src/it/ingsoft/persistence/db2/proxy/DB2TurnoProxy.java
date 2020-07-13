package it.ingsoft.persistence.db2.proxy;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import it.ingsoft.model.relations.StrutturaTurnoMappingDAO;
import it.ingsoft.model.relations.TurnoTempoMappingDAO;
import it.ingsoft.model.struttura.Struttura;
import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.turno.Turno;
import it.ingsoft.model.turno.TurnoDAO;
import it.ingsoft.persistence.DBInstance;
import it.ingsoft.persistence.FactoryDAO;
import it.ingsoft.persistence.db2.DB2FactoryDAO;

public class DB2TurnoProxy extends Turno {
	
	static
	{
		FactoryDAO factory = FactoryDAO.getDAOFactory(DBInstance.DB2);
		
		try
		{
			TurnoDAO turnoDAO = factory.getTurnoDAO();
			turnoDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella turni");
		}
		
		try
		{
			TurnoTempoMappingDAO turTemDAO = factory.getTurnoTempoMappingDAO();
			turTemDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella relazione turni e tempi");
		}
		
		try
		{
			StrutturaTurnoMappingDAO strTurDAO = factory.getStrutturaTurnoMappingDAO();
			strTurDAO.createTable();
			
		} catch (SQLException e) {
			System.out.println("Trovata tabella relazione strutture e turni");
		}
	}
	
	public DB2TurnoProxy(int id) {
		super();
		
		try
		{
			TurnoDAO turnoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoDAO();
			Turno newTurno = turnoDAO.get(id);
			
			super.setId(id);
			super.setFine(newTurno.getFine());
			super.setInizio(newTurno.getInizio());
			super.setPostiDisponibili(newTurno.getPostiDisponibili());
			super.setPrezzo(newTurno.getPrezzo());
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare il turno: " + e.getMessage());
			
			super.setId(id);
			
			try
			{
				TurnoDAO turnoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoDAO();
				turnoDAO.insert(this);
				
			} catch(SQLException ee) {
				System.err.println("Impossibile salvare il turno: " + ee.getMessage());
			}
		}
	}
	
	@Override
	public List<Tempo> getTempi() {
		List<Tempo> result = null;
		
		try
		{
			TurnoTempoMappingDAO turTemDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoTempoMappingDAO();
			result = turTemDAO.getTempi(this);
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i tempi: " + e.getMessage());
			result = new ArrayList<>();
		}
		
		return result;
	}
	
	@Override
	public void setTempi(List<Tempo> tempi) {
		super.setTempi(tempi);
		
		try
		{
			TurnoTempoMappingDAO turTemDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoTempoMappingDAO();
			
			for(Tempo t : tempi)
			{	
				turTemDAO.insert(this, t);
			}
			
		} catch(SQLException e) {
			System.err.println("Impossibile caricare i tempi: " + e.getMessage());
		}
	}
	
	@Override
	public Struttura getStruttura() {
		Struttura result = null;
		
		try {
			StrutturaTurnoMappingDAO strTurDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaTurnoMappingDAO();
			result = strTurDAO.getStruttura(this);
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare la struttura: " + e.getMessage());
			result = null;
		}
		
		return result;
	}
	
	@Override
	public void setStruttura(Struttura struttura) {
		super.setStruttura(struttura);
		
		try {
			StrutturaTurnoMappingDAO strTurDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getStrutturaTurnoMappingDAO();
			strTurDAO.insert(struttura, this);
			
		} catch (SQLException e) {
			System.err.println("Impossibile salvare la struttura: " + e.getMessage());
		}
	}
	
	@Override 
	public void setId(int id) {
		throw new InvalidUseException("Metodo non utilizzabile per la sincronia remota");
	}
	
	@Override
	public void setFine(LocalDateTime fine) {
		super.setFine(fine);
		this.update();
	}
	
	@Override
	public void setInizio(LocalDateTime inizio) {
		super.setInizio(inizio);
		this.update();
	}
	
	@Override
	public void setPostiDisponibili(int postiDisponibili) {
		super.setPostiDisponibili(postiDisponibili);
		this.update();
	}
	
	@Override
	public void setPrezzo(float prezzo) {
		super.setPrezzo(prezzo);
		this.update();
	}
	
	private void update()
	{
		try {
			TurnoDAO turnoDAO = DB2FactoryDAO.getDAOFactory(DBInstance.DB2).getTurnoDAO();
			turnoDAO.update(this);
			
		} catch (SQLException e) {
			System.err.println("Impossibile caricare il turno: " + e.getMessage());
		}
	}
}
