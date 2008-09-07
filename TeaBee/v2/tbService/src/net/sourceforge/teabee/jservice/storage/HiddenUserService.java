package net.sourceforge.teabee.jservice.storage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sourceforge.teabee.jservice.login.User;

public class HiddenUserService {

	private static final HiddenUserService INSTANCE = new HiddenUserService();
	
	private final Set<User> data = new HashSet<User>();
	
	private final Map<String, User> usernameMapping = new HashMap<String, User>();
	
	private HiddenUserService() {
		// singleton
	}
	
	public static HiddenUserService getInstance() {
		return INSTANCE;
	}
	
	
	public void addUser(final User user) {
		this.data.add(user);
		this.usernameMapping.put(user.getUsername(), user);
	}
	
	
	public void updateUser(final User user) {
		// used to:
		// - update ticket field when logged in
		// - update ticket lifetime, each x-requests
		// (- maybe change password)
		this.data.add(user);
	}
	
	public void deleteUser(final User user) {
		this.data.remove(user);
	}
	
	
	public User getUserByUsername(final String username) { // , final String passwordHash
		return this.usernameMapping.get(username);
	}
}
