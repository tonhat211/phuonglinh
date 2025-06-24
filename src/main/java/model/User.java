package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
    public int id;
    public String name;
    public String email;
    public String pwd;
    public String phone;
    public LocalDate dob;
    public int gender;
    public ArrayList<String> roles;
    public LocalDate dateIn;
    public int avai;

    public User(int id, String name, String email, String pwd, String phone, LocalDate dob, int gender, ArrayList<String> roles, LocalDate dateIn, int avai) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pwd = pwd;
        this.phone = phone;
        this.dob = dob;
        this.gender = gender;
        this.roles = roles;
        this.dateIn = dateIn;
        this.avai = avai;
    }

    //Comment
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // add
    public User(String name, String email, String pwd) {
        this.name = name;
        this.email = email;
        this.pwd = pwd;
    }

    public User(int id) {
        this.id = id;
    }

    public String getGenderString() {
        return Constant.getGenderString(gender);
    }

    public String checkRole() {
        if(roles.isEmpty()) return null;
        return roles.get(0);
    }

    public boolean hasAnyRole() {
        if(roles.isEmpty()) return false;
        return true;
    }

    public boolean hasRole(String rolein) {
        if(roles.isEmpty()) return false;
        for(String role : roles) {
            if(role.equalsIgnoreCase(rolein)) return true;
        }
        return false;

    }

    public boolean isLocked() {
        if(avai == Constant.UNACTIVE) return true;
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", pwd='" + pwd + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                ", gender=" + gender +
                ", roles=" + roles +
                ", dateIn=" + dateIn +
                ", avai=" + avai +
                '}';
    }

    public String getRolesJson() {
        if(roles==null) return null;
        Gson gson = new Gson();
        String json = gson.toJson(roles);
        return json;
    }

    public static void main(String[] args) {
        String roles ="[\"customer\",\"employee\",\"product\",\"order\"]";
        System.out.println(roles.contains("CUSTOMER"));
    }


}
