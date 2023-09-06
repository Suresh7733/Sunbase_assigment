package com.sunbase.assigment.authentication;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Authenticate {
	  public String authenticateUserAndGetToken(String username, String password) {
	        String authApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp";
	    
	        HttpClient httpClient = HttpClients.createDefault();
	        HttpPost httpPost = new HttpPost(authApiUrl);
	    
	        try {
	            String requestBody = "{\"login_id\": \"" + username + "\", \"password\": \"" + password + "\"}";
	            StringEntity entity = new StringEntity(requestBody);
	            httpPost.setEntity(entity);
	            httpPost.setHeader("Accept", "application/json");
	            httpPost.setHeader("Content-type", "application/json");
	    
	            HttpResponse response = httpClient.execute(httpPost);
	    
	            if (response.getStatusLine().getStatusCode() == 200) {
	                String responseBody = EntityUtils.toString(response.getEntity());
	                System.out.println(responseBody);
	            } else {
	                // Handle authentication error
	                return null;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
	        return null;
	    }
	       
}
