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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSecond() {
        return userSecond;
    }

    public void setUserSecond(String userSecond) {
        this.userSecond = userSecond;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAdress() {
        return userAdress;
    }

    public void setUserAdress(String userAdress) {
        this.userAdress = userAdress;
    }

    public String getUserTown() {
        return userTown;
    }

    public void setUserTown(String userTown) {
        this.userTown = userTown;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }
}
