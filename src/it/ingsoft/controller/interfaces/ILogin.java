package it.ingsoft.controller.interfaces;

public interface ILogin {
	public boolean verificaCredenziali(String username, String password);
	public void logout();
}
