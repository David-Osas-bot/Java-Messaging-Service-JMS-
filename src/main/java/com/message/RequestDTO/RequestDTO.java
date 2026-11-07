package com.message.RequestDTO;

public class RequestDTO {

    private int id;
    private String fullname;
    private String phone;
    private String email;
    private String password;

    public RequestDTO() {

    }

    public RequestDTO(int id, String fullname, String phone, String email, String password) {
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public int getId(){
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public String getFullName(){
        return fullname;
    }
    
    public void setFullName(String fullname){
        this.fullname = fullname;
    }
    
    public String getPhone(){
        return phone;
    }
    
    public void setPhone(String phone){
        this.phone = phone;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
}
