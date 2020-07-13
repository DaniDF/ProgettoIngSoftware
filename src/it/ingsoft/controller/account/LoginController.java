package it.ingsoft.controller.account;

import it.ingsoft.controller.interfaces.ILogin;
import it.ingsoft.persistence.db2.proxy.DB2CredentialsProxyDAO;

public class LoginController implements ILogin {

	@Override
	public boolean verificaCredenziali(String username, String password) {
		return new DB2CredentialsProxyDAO(username).checkCredentials(password);
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
	}

}
