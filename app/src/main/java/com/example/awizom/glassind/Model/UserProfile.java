package com.example.awizom.glassind.Model;

public class UserProfile {
    private String Id;
    private String Email;
    private String Role;
    private boolean Active;

    public UserProfile(String id, String email, String role, boolean active) {
        Id = id;
        Email = email;
        Role = role;
        Active = active;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }



    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean active) {
        Active = active;
    }
}
