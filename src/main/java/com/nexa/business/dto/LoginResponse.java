package com.nexa.business.dto;
import java.util.Collections;
import java.util.List;
public class LoginResponse {
 private final String username,displayName,jobTitle,redirect; private final List<String> roles; private final boolean authenticated;
 public LoginResponse(String u,String d,String j,String r,String redirect){this.username=u;this.displayName=d;this.jobTitle=j;this.roles=Collections.singletonList(r);this.redirect=redirect;this.authenticated=true;}
 public String getUsername(){return username;} public String getDisplayName(){return displayName;} public String getJobTitle(){return jobTitle;} public List<String> getRoles(){return roles;} public boolean isAuthenticated(){return authenticated;} public String getRedirect(){return redirect;}
}
