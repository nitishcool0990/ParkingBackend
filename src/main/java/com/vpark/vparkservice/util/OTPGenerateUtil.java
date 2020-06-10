package com.vpark.vparkservice.util;

import java.util.Random;

public class OTPGenerateUtil {
	 static Random rndm_method = new Random();
	 public static String OTP(int len) 
	    { 
	        System.out.println("Generating OTP using random() : "); 
	        System.out.print("You OTP is : "); 
	  
	        // Using numeric values 
	        String numbers = "0123456789"; 
	  
	        // Using random method 
	  
	      //  char[] otp = new char[len]; 
	       String otp="";
	  
	        for (int i = 0; i < len; i++) 
	        { 
	            // Use of charAt() method : to get character value 
	            // Use of nextInt() as it is scanning the value as int 
	             
	        	otp=otp+ rndm_method.nextInt(numbers.length()); 
	        	System.out.println(otp);
	        } 
	        return otp; 
	    } 
	    public static void main(String[] args) 
	    { 
	        int length = 4; 
	        System.out.println(OTP(length)); 
	    } 

}
