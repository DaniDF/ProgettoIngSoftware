package it.ingsoft.model.relations;

import java.sql.SQLException;
import java.util.List;

import it.ingsoft.model.fattura.Fattura;
import it.ingsoft.model.utente.Utente;

public interface UtenteFatturaMappingDAO {
	public void createTable() throws SQLException;
	public void dropTable() throws SQLException;
	
	public void insert(Utente utente, Fattura fattura) throws SQLException;
	public void delete(Utente utente, Fattura fattura) throws SQLException;
	public List<Fattura> getFatture(Utente utente) throws SQLException;
	public Utente getUtente(Fattura fattura) throws SQLException;
}
