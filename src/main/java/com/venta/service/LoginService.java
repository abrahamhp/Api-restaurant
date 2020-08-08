package com.venta.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

 
@Service
public class LoginService {
	
	@Autowired Environment env;
	
	public boolean validateUser(String userid, String password) {
        // usuario y password
		
		return userid.equalsIgnoreCase( env.getProperty("login.user")) && password.equalsIgnoreCase(env.getProperty("login.password"));
    }

}
