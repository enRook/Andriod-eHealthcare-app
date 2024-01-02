package com.sicTLC.myapplication;
public class User {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String birthdate;
    private String userRole;
    private String age;

    public User(String id, String username, String firstName, String lastName,
                String email, String birthdate, String userRole, String age) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.userRole = userRole;
        this.age = age;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getAge() {
        return age;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
