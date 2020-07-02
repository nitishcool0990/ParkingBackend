package com.vpark.vparkservice.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class ReferalCodeUtil {
	
	private  Random rnd = new Random();
	
	public  String getSaltString(int length) {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder salt = new StringBuilder();

		while (salt.length() < length) { // length of the random string.
		int index = (int) (rnd.nextFloat() * SALTCHARS.length());
		salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

		}

}
