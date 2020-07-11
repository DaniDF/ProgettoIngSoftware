package it.ingsoft.servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.ingsoft.model.utente.Utente;

public class Signin extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] param = new String[13];
		
		param[0] = (String)req.getParameter("nome");
		param[1] = (String)req.getParameter("cognome");
		param[2] = (String)req.getParameter("dat_nasc");
		param[3] = (String)req.getParameter("luo_nasc");
		param[4] = (String)req.getParameter("cod_fisc");
		param[5] = (String)req.getParameter("nazione");
		param[6] = (String)req.getParameter("provincia");
		param[7] = (String)req.getParameter("citta");
		param[8] = (String)req.getParameter("via");
		param[9] = (String)req.getParameter("num_civ");
		param[10] = (String)req.getParameter("cap");
		param[11] = (String)req.getParameter("usr");
		param[12] = (String)req.getParameter("psw");
		
		boolean flagErr = false;
		
		for(int cont = 0; !flagErr && cont < param.length; cont++)
		{
			flagErr = (param[cont] == null);
		}
		
		if(!flagErr)
		{
			Utente newUtente = new Utente();
			try
			{
				newUtente.setNome(param[0]);
				newUtente.setCognome(param[1]);
				newUtente.setDataDiNascita(LocalDate.parse(param[2]));
				newUtente.setLuogoDiNascita(param[3]);
				newUtente.setCodiceFiscale(param[4]);
				newUtente.setNazione(param[5]);
				newUtente.setProvincia(param[6]);
				newUtente.setCitta(param[7]);
				newUtente.setVia(param[8]);
				newUtente.setNumeroCivico(param[9]);
				newUtente.setCap(param[10].toCharArray());
				
				req.getSession().setAttribute("utente", newUtente);
			
			} catch(NumberFormatException | DateTimeParseException e) {
				flagErr = true;
			}
		}
		
		if(!flagErr)
		{
			resp.addCookie(new Cookie("usr", param[11]));
			resp.sendRedirect("/home/");
		}
		else
		{
			//resp.sendRedirect();
		}
	}
}
