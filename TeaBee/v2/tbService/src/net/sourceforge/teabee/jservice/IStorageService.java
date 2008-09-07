package net.sourceforge.teabee.jservice;

import net.sourceforge.teabee.jservice.search.SessionTicket;
import net.sourceforge.teabee.jservice.storage.Library;

public interface IStorageService {
	
	void saveLibrary(final SessionTicket ticket, final Library library);
	
	Library getLibrary(final SessionTicket ticket);
	
}
