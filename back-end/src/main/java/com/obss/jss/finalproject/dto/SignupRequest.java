package com.obss.jss.finalproject.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {

  @NotBlank
  @Size(min = 3, max = 20)
  private String username;
  
  private Boolean admin;

  private Set<String> role;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
  
  public Set<String> getRole() {
	return role;
  }

	public void setRole(Set<String> role) {
		this.role = role;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}
}
