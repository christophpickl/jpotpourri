package net.sourceforge.teabee.jservice.storage;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.teabee.jservice.IStorageService;
import net.sourceforge.teabee.jservice.login.AbstractUserService;
import net.sourceforge.teabee.jservice.login.User;
import net.sourceforge.teabee.jservice.search.SessionTicket;

public class StorageService extends AbstractUserService implements IStorageService {

	// TODO use real persistence (db4o, hibernate)
	
	private final Map<User, Library> data = new HashMap<User, Library>();
	
	public StorageService() {
		// nothing to do
	}
	
	public void saveLibrary(final SessionTicket ticket, final Library library) {
		final User user = this.getUserForTicket(ticket);
		
		this.data.put(user, library);
	}
	
	public Library getLibrary(final SessionTicket ticket) {
		final User user = this.getUserForTicket(ticket);
		
		return this.data.get(user);
	}
	
}
