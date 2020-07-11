package it.ingsoft.model.relations;

import java.sql.SQLException;
import java.util.List;

import it.ingsoft.model.tempo.Tempo;
import it.ingsoft.model.utente.Utente;

public interface UtenteTempoMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Utente utente, Tempo tempo) throws SQLException;
	public void delete(Utente utente, Tempo tempo) throws SQLException;
	public List<Tempo> getTempi(Utente utente) throws SQLException;
	public Utente getUtente(Tempo tempo) throws SQLException;
}
