package com.sunbase.assigment.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunbase.assigment.user.Customer;
import com.sunbase.assigment.user.AuthRequest;



@RestController
@RequestMapping("/")
public class Controller {
	  private final RestTemplate restTemplate;
	  private String uuid ;
	    private  String token;
	   
	    public Controller(RestTemplate restTemplate){
	this.restTemplate =restTemplate;
	    }
	  

	@GetMapping("/")
		public ModelAndView index(Model model) {
		ModelAndView modelAndView = new ModelAndView();
			  modelAndView.setViewName("index.html");
		      return modelAndView;
	}
	@GetMapping("/creat")
	    public ModelAndView showCreateForm() {
	        ModelAndView modelAndView = new ModelAndView();
	         modelAndView.setViewName("CreateCutomer.html");
	        return modelAndView;
	    }



	@GetMapping("/getCustomers")
	public ModelAndView getCustomerList(Model model) {
	    String customerListEndpoint = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=get_customer_list";
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Authorization", "Bearer " + this.token);

	    HttpEntity<String> requestEntity = new HttpEntity<>(headers);

	    ResponseEntity<Customer[]> response = restTemplate.exchange(
	            customerListEndpoint,
	            HttpMethod.GET,
	            requestEntity,
	            Customer[].class
	    );

	ModelAndView modelAndView = new ModelAndView();
			  
		     
	    if (response.getStatusCode() == HttpStatus.OK) {
	        Customer[] customers = response.getBody();
	        model.addAttribute("customers", customers);
	        modelAndView.setViewName("customers.html");
	    } else {
	       modelAndView.setViewName("error"); // Handle the error case
	    } 
	    return modelAndView;
	}

	@PostMapping("/login")
	    public RedirectView authenticateUser(@RequestParam String username, @RequestParam String password) {
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        AuthRequest authRequest = new AuthRequest(username,password);
	        String authApiUrl = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment_auth.jsp"; // Hardcoded URL

	        RestTemplate restTemplate = new RestTemplate();
	        HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(authRequest, headers);

	        ResponseEntity<String> responseEntity = restTemplate.exchange(authApiUrl, HttpMethod.POST, requestEntity, String.class);
	 if (responseEntity.getStatusCode() == HttpStatus.OK) {
	            String responseBody = responseEntity.getBody();

	            ObjectMapper objectMapper = new ObjectMapper();
	            try {
	                JsonNode responseJson = objectMapper.readTree(responseBody);
	                String token = responseJson.get("access_token").asText();
	                this.token =token;
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        return new RedirectView("/getCustomers");
	    }

	      @PostMapping("/create")
	    public ResponseEntity<String> createRecord(@RequestParam Map<String, String> formData) {
	      String createApiUrl ="https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=create";
	       HttpHeaders headers = new HttpHeaders();
	         headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Authorization", "Bearer " +this.token);         
	       Customer customer= new Customer(formData);
	        HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);
	        ResponseEntity<String> responseEntity;
	      
	            try {
	                responseEntity = restTemplate.exchange(
	                    createApiUrl,
	                    HttpMethod.POST,
	                    requestEntity,
	                    String.class
	                );
	             return responseEntity;
	          
	            } 
	            catch (HttpStatusCodeException e) {
	 	            
	              return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        }
	             catch (Exception ex) {
	               String errorMessage = ex.getMessage() + "An Error Occure";
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	            }
	            

	} 
 
	      @PostMapping("/delete")
        public ResponseEntity<String> delete(@RequestParam("customerId") String customerId) {
	    	String url = "https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=delete&uuid="+customerId;
	    	  HttpHeaders headers = new HttpHeaders(); 
	    	  headers.set("Authorization", "Bearer " +this.token); 
	    	  HttpEntity<AuthRequest> requestEntity = new HttpEntity<>(headers);
	    	 try {
	    	  ResponseEntity<String>responseEntity =restTemplate.exchange(url,HttpMethod.POST, requestEntity,String.class);
	    	  return responseEntity;
	    	 }
	    	 catch (HttpStatusCodeException e) {
	 	            
	              return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        }
	    	 catch(Exception ex) {
	    		 String errorMessage = "Error , Not deleted";
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
	    	 }
	    	
	      }
	      
         @PostMapping("/edit")
	    public  ModelAndView edit(
	    		@RequestParam("customerId") String  customerId,
	            @RequestParam("first_name") String first_name,
	            @RequestParam("last_name") String last_name,
	            @RequestParam("street") String street,
	            @RequestParam("address") String address,
	            @RequestParam("city") String city,
	            @RequestParam("email") String email,
	            @RequestParam("phone") String phone,
	            Model model
	        ) {
        	 Customer customer = new Customer();
             customer.setUuid(customerId);
             customer.setFirst_name(first_name);
             customer.setLast_name(last_name);
             customer.setStreet(street);
             customer.setAddress(address);
             customer.setCity(city);
             customer.setEmail(email);
             customer.setPhone(phone);
             this.uuid=customerId;
        	 model.addAttribute("customer", customer);
        	 ModelAndView modelAndView = new ModelAndView();
			  modelAndView.setViewName("update.html");
        	 
	 return modelAndView;
	    }
//  
         @PostMapping("/update")
  public ResponseEntity<String> update(@RequestParam Map<String, String> formData){
        String url ="https://qa2.sunbasedata.com/sunbase/portal/api/assignment.jsp?cmd=update&uuid="+this.uuid;
        	 HttpHeaders headers=  new HttpHeaders();
        	 headers.setContentType(MediaType.APPLICATION_JSON);
        	 headers.set("Authorization","Bearer "+this.token);
        	 Customer customer= new Customer(formData);
        	 HttpEntity<Customer> requestEntity = new HttpEntity<>(customer, headers);
 	        ResponseEntity<String> responseEntity;
 	    
 	            try {
 	                responseEntity = restTemplate.exchange(
 	                    url,
 	                    HttpMethod.POST,
 	                    requestEntity,
 	                    String.class
 	                );
 	                System.out.println(responseEntity.getStatusCodeValue());
 	               if (responseEntity.getStatusCode() == HttpStatus.OK) {
 	                 
 	                  return ResponseEntity.ok(responseEntity.getBody());
 	              } else if (responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
 	                  
 	                  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
 	              } else {
 	                
 	                  return ResponseEntity.status(responseEntity.getStatusCode()).body("Unhandled Status Code: " + responseEntity.getStatusCode());
 	              }
 	          } catch (HttpStatusCodeException e) {
 	            
 	              return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
         }
 	            catch(Exception ex) {
 	            	 String errorMessage = "An Error Occure"+ex.getMessage();
 	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
 	    	
 	            }
}

         
     
}
