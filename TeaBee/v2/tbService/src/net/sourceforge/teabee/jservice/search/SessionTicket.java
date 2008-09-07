package net.sourceforge.teabee.jservice.search;

import java.util.Date;

import net.sourceforge.teabee.jservice.login.User;

public class SessionTicket {

	private final String ticket;

	private SessionTicket(String ticket) {
		this.ticket = ticket;
	}
	
	public static SessionTicket newRandom(final User user) {
		final StringBuilder sb = new StringBuilder(80);
		
		sb.append(user.getUsername());
		sb.append("xxxx");
		sb.append(new Date().toString());
		
		return new SessionTicket(sb.toString());
	}

	public String getTicket() {
		return this.ticket;
	}
	
	
}
