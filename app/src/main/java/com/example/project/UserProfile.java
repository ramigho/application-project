package com.example.project;

public class UserProfile {
    public String userName;
    public String userSecond;
    public String userPhone;
    public String userAdress;
    public String userTown;
    public String userPost;

    public UserProfile(){
    }

    public UserProfile(String userName, String userSecond, String userPhone, String userAdress, String userTown, String userPost){
        this.userName = userName;
        this.userSecond = userSecond;
        this.userPhone = userPhone;
        this.userAdress = userAdress;
        this.userTown = userTown;
        this.userPost = userPost;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSecond() {
        return userSecond;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public String getUserTown() {
        return userTown;
    }

    public String getUserPost() {
        return userPost;
    }
}
