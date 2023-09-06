package com.sunbase.assigment.user;

public class AuthRequest {
	private String login_id;
    private String password;
    public AuthRequest(String login_id,String password){
this.login_id = login_id;
this.password =password;
    }
    public void setLogin_id(String login_id){
     this.login_id =login_id;
    }
    public String getLogin_id(){
        return this.login_id;
    }
    public void setPassword(String password){
                       this.password =password;
    }
    public String getPassword(){
        return this.password;
    }

}
