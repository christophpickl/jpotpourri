package net.sourceforge.teabee.jservice.login;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.teabee.jservice.search.SessionTicket;

/**
 * for use within jservice only
 */
class SessionManager {

	private static final SessionManager INSTANCE = new SessionManager();
	
	private final Map<SessionTicket, User> ticketMap = new HashMap<SessionTicket, User>();
	
	private SessionManager() {
		// singleton
	}
	
	public static SessionManager getInstance() {
		return INSTANCE;
	}
	
	public void register(final SessionTicket ticket, final User user) {
		final User oldUser = this.ticketMap.put(ticket, user);
		if(oldUser != null) {
			throw new RuntimeException("duplicate ticket ["+ticket+"]!");
		}
	}
	
	public void unregister(final SessionTicket ticket) {
		final User removedUser = this.ticketMap.remove(ticket);
		if(removedUser == null) {
			throw new RuntimeException("invalid ticket ["+ticket+"]!");
		}
	}
	
	public boolean isValid(final SessionTicket ticket) {
		return this.ticketMap.containsKey(ticket);
	}
	
	public User getUser(final SessionTicket ticket) {
		final User user = this.ticketMap.get(ticket);
		if(user == null) {
			throw new RuntimeException("invalid ticket ["+ticket+"]!");
		}
		
		return user;
	}
}
