package net.sourceforge.teabee.jservice.login;

import net.sourceforge.teabee.jservice.search.SessionTicket;
import net.sourceforge.teabee.jservice.storage.HiddenUserService;

public class LoginService {

	
	public LoginResult doLogin(final String username, final String passwordHash) {
		
		final User user = HiddenUserService.getInstance().getUserByUsername(username);
		final boolean successfull = user != null; // FIXME use HiddenUserService for validation
		final SessionTicket ticket;
		
		if(successfull == true) {
			 // TODO session ticket by: username + password + currentTimestamp
			ticket = SessionTicket.newRandom(user);
		} else {
			ticket = null;
		}
		
		return new LoginResult(successfull, ticket);
	}
	
}
