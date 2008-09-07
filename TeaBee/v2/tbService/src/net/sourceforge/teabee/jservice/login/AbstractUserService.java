package net.sourceforge.teabee.jservice.login;

import net.sourceforge.teabee.jservice.search.SessionTicket;

public abstract class AbstractUserService {

	protected User getUserForTicket(final SessionTicket ticket) {
		if(SessionManager.getInstance().isValid(ticket) == false) {
			throw new RuntimeException("invalid ticket ["+ticket+"]!");
		}
		return SessionManager.getInstance().getUser(ticket);
	}
}
