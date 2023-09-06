package com.sunbase.assigment.user;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonInclude;

public class Customer {
	  private String uuid;
	    private String first_name;
	    private String last_name;
	    private String street;
	    private String address;
	    private String city;
	    private String state;
	    private String email;
	    private String phone;
		public Customer(){}
		public    Customer(Map<String,String>map){
			
			if (map.containsKey("first_name") && !map.get("first_name").isEmpty()) {
		        this.first_name = map.get("first_name");
		    }
		    if (map.containsKey("last_name") && !map.get("last_name").isEmpty()) {
		        this.last_name = map.get("last_name");
		    }
			this.street= map.get("street");
			this.address =map.get("address");
			this.city= map.get("city");
			this.state =map.get("state");
			this.email= map.get("email");
			this.phone =map.get("phone");
			 if (this.first_name == null) {
			        this.first_name = null;
			    }
			    if (this.last_name == null) {
			        this.last_name = null;
			    }
		}
		 @JsonInclude(JsonInclude.Include.NON_NULL)
	    public String getFirst_name() {
			return first_name;
		}
		 @JsonInclude(JsonInclude.Include.NON_NULL)
		public void setFirst_name(String first_name) {
			this.first_name = first_name;
		}
		 @JsonInclude(JsonInclude.Include.NON_NULL)
		public String getLast_name() {
			return last_name;
		}
		 @JsonInclude(JsonInclude.Include.NON_NULL)
		public void setLast_name(String last_name) {
			this.last_name = last_name;
		}
		public String getStreet() {
			return street;
		}
		public void setStreet(String street) {
			this.street = street;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	    public  void setPhone(String phone){
	        this.phone =phone;
	    }
	    public String getPhone(){
	        return this.phone;
	    }
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		
}
