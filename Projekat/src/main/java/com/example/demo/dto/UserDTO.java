package com.example.demo.dto;

public class UserDTO {

	private String lozinka;

	private String email;
	
	private Uloga uloga;

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Uloga getUloga() {
		return uloga;
	}

	public void setUloga(Uloga uloga) {
		this.uloga = uloga;
	}

	@Override
	public String toString() {
		return "UserDTO [lozinka=" + lozinka + ", email=" + email + ", uloga=" + uloga + "]";
	}
	
	
	
}
