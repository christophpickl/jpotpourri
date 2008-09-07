package net.sourceforge.teabee.jservice.login;

public class User {

	private final String username;
	private final String passwordHash;
	
	public User(String username, String passwordHash) {
		this.username = username;
		this.passwordHash = passwordHash;
	}
	public String getUsername() {
		return this.username;
	}
	public String getPasswordHash() {
		return this.passwordHash;
	}
	
	
	
}
