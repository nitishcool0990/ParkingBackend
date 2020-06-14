package com.vpark.vparkservice.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "common.data")
public class CommonProperties {
	
	private String otpURL;
	private String otpApiKey;
	private boolean isBuildForTest;
	
	public String getOtpURL() {
		return otpURL;
	}
	public void setOtpURL(String otpURL) {
		this.otpURL = otpURL;
	}
	public String getOtpApiKey() {
		return otpApiKey;
	}
	public void setOtpApiKey(String otpApiKey) {
		this.otpApiKey = otpApiKey;
	}
	public boolean isBuildForTest() {
		return isBuildForTest;
	}
	public void setIsBuildForTest(boolean isBuildForTest) {
		this.isBuildForTest = isBuildForTest;
	}
	
	

}
