package com.vpark.vparkservice.urlcalling;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
import com.vpark.vparkservice.constants.IConstants;
import com.vpark.vparkservice.entity.OtpApiErrorLogs;
import com.vpark.vparkservice.model.OTPResponse;
import com.vpark.vparkservice.repository.IOtpApiErrorLogs;

@Component
public class URLCaller {
	

//	private static final Logger logger = LogManager.getLogger(URLCaller.class);
	
	
	@Autowired
	private IOtpApiErrorLogs apiErrorLogsDao;
	

	private static final String USER_AGENT = "Mozilla/5.0";	
	//Gson gson = new Gson();
		
	public OTPResponse callGetURL(String url, int userId, String token) {
		OTPResponse otpResponse = null;

		if (url == null) {
			System.out.println("URLCaller :callGetURL(), URL is null");
			return null;
		}

	
		HttpURLConnection con = null;
		BufferedReader in =null;
		try {
			URL obj = new URL(addQueryStringToUrlString(url, null));
			System.out.println("URL:" + obj);
			con = (HttpURLConnection) obj.openConnection();
			con.setConnectTimeout(2 * 1000);
			con.setRequestMethod("GET");
			con.setRequestProperty(IConstants.USER_AGENT, USER_AGENT);
			int responseCode = con.getResponseCode();
			System.out.println("GET Response Code :: " + responseCode);
			if (responseCode == HttpURLConnection.HTTP_OK) { // success
				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();

				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				// print result
				System.out.println(response.toString());
				System.out.println("Response:" + response.toString());
				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				otpResponse = mapper.readValue(response.toString(), OTPResponse.class);

				// System.out.println(mystat.getRespData().getFlopSeen());

			} else {
				setDataInErrorTable(userId, url);
				System.out.println("GET request not worked url:" + url);
			}
		} catch (Exception ex) {
			System.out.println("SendGetTask-run, Exception while sending Get command"+ex);
		}finally {
			if(con !=null) {
				con.disconnect();
				if(in!=null ) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("SendGetTask-run, Exception while closing buffered reader in finally "+ e);
					}
				}
			}
		}
		return otpResponse;
	}
	
	
	private String addQueryStringToUrlString(String url, final Map<String, String> parameters) throws UnsupportedEncodingException {
	    if (parameters == null) {
	        return url;
	    }

	    for (Map.Entry<String, String> parameter : parameters.entrySet()) {

	        final String encodedKey = URLEncoder.encode(parameter.getKey().toString(), IConstants.UTF_8);
	        final String encodedValue = URLEncoder.encode(parameter.getValue().toString(), IConstants.UTF_8);

	        if (!url.contains("?")) {
	            url += "?" + encodedKey + "=" + encodedValue;
	        } else {
	            url += "&" + encodedKey + "=" + encodedValue;
	        }
	    }

	    return url;
	}
	
	public boolean callPostURL(String url,Map<String, Object> params) {
		boolean ret = false;
		HttpURLConnection con = null;
		BufferedReader in =null;
		DataOutputStream wr = null;
		String urlParameters = null;
		try {

			URL obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();
			
			/*
			 * Map<String, Object> params = new HashMap<String, Object>();
			 * params.put(Constants.USER_ID, userId); params.put(Constants.CROWNS, crowns);
			 * params.put(Constants.REMARKS, Constants.CROWN_API_REMARKS);
			 * params.put(Constants.EVENT_NAME, Constants.CROWN_API_EVENT_NAME);
			 */			

			// add reuqest header
			con.setConnectTimeout(2 * 1000);
			con.setRequestMethod(IConstants.POST);
			con.setRequestProperty(IConstants.USER_AGENT, USER_AGENT);
			con.setRequestProperty(IConstants.CONTENT_TYPE, IConstants.CONTENT_VALUE);
			// con.setRequestProperty("Authorization", "1f7cc6648f5dbb59fdfb60048fa7097cdcbf1350d449a7da89c7d84ee7949322");
			// con.setRequestProperty("Accesskey", Constants.ACCESS_TOKEN);

		//	String urlParameters = postUrlParameters(params);
			ObjectMapper mapperObj = new ObjectMapper();

			urlParameters = mapperObj.writeValueAsString(params);


			// Send post request
			con.setDoOutput(true);
			wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			
			if(responseCode == 200) {
				ret = true;
			}else {
			//	setDataInErrorTable(params, urlParameters);
			}
			
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			
			if(!(response.toString().contains("Success"))) {
			//	setDataInErrorTable(params, urlParameters);
			}
			
			in.close();

			// print result
			System.out.println(response.toString());
			System.out.println("response from :" + url + "  :  "+ response.toString());
		} catch (Exception e) {		
			try {
			//	setDataInErrorTable(params, urlParameters);
			}catch(Exception ex) {
				System.out.println("Exception in saving data in cpRafApiErrorLogs"+ e);
			}
			System.out.println("Error During Post URL call"+e);
			System.out.println("Exception in callPostURL"+ e);
		}finally {
			if(con !=null) {
				con.disconnect();
				if(wr!=null ) {
					try {
						wr.close();
					} catch (IOException e) {
						System.out.println("SendGetTask-run, Exception while closing dataoutputstream in finally "+ e);
					}
				}
				if(in!=null ) {
					try {
						in.close();
					} catch (IOException e) {
						System.out.println("SendGetTask-run, Exception while closing buffered reader in finally "+ e);
					}
				}
			}
		}
		
		return ret;

	}


	private void setDataInErrorTable(int userId, String urlParameters) {
		Date date = new Date();
		OtpApiErrorLogs cpRafApiErrorLogs = new OtpApiErrorLogs();
		
		cpRafApiErrorLogs.setUserId(userId);
		cpRafApiErrorLogs.setRequest(urlParameters);
		cpRafApiErrorLogs.setStatus("PENDING");
		cpRafApiErrorLogs.setLinuxModifiedOn(date.getTime()/1000);
		cpRafApiErrorLogs.setModifiedOn(date);
		
		apiErrorLogsDao.save(cpRafApiErrorLogs);
	}

	private String postUrlParameters(Map<String, Object> parameters) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance(IConstants.SHA_256);
			
		//	String userIdStr = parameters.get(Constants.USER_ID).toString() + commonProperties.getPrivateKey();
			
		//	byte[] hashInBytes = md.digest(userIdStr.getBytes(StandardCharsets.UTF_8));

			// bytes to hex
			/*
			 * StringBuilder sb = new StringBuilder(); for (byte b : hashInBytes) {
			 * sb.append(String.format("%02x", b)); } System.out.println(sb.toString());
			 * parameters.put(Constants.KEY, sb.toString());
			 */
		} catch (NoSuchAlgorithmException e) {			
			e.printStackTrace();
			System.out.println("Error During Post parameters creation"+e);
		}

		//return gson.toJson(parameters);
		return null;

	}	
	
	public static void main(String[] args) throws JsonProcessingException {
		
		 URLCaller urlcall = new URLCaller();
		 urlcall.callGetURL("http://2factor.in/API/V1/d83d4e6c-9e4f-11ea-9fa5-0200cd936042/SMS/9958801420/4499", -1, null);
		 
		
	}
	

}
