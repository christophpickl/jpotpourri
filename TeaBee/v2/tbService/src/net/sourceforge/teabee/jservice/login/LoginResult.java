package net.sourceforge.teabee.jservice.login;

import net.sourceforge.teabee.jservice.search.SessionTicket;

public class LoginResult {

	private final boolean successfull;
	
	private final SessionTicket ticket;

	public LoginResult(boolean successfull, SessionTicket ticket) {
		this.successfull = successfull;
		this.ticket = ticket;
	}

	public boolean isSuccessfull() {
		return this.successfull;
	}

	public SessionTicket getTicket() {
		return this.ticket;
	}
	
	
	
}
