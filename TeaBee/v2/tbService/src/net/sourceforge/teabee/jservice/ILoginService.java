package net.sourceforge.teabee.jservice;

import net.sourceforge.teabee.jservice.login.LoginResult;

public interface ILoginService {

	LoginResult doLogin(final String username, final String passwordHash);
	
}
