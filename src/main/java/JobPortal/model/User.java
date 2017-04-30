package JobPortal.model;

import javax.persistence.*;
import java.util.*;

import JobPortal.controller.*;
//import JobPortal.service.*;

/**
 * Created by anvita on 4/18/17.
 */
@Entity
@Table(name = "user")
public class User {

    //private attributes

    @Id
    @Column(name="userId")
    private int userId;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="email")
    private String email;

    @Column(name="profile_image")
    private String profile_image;

    @Column(name="self_intro")
    private String self_intro;

    @Column(name="work_ex")
    private Float work_ex;

    @Column(name="max_education")
    private String max_education;

    @Column(name="current_status")
    private String status;

    @Column(name="skills")
    private String skills;

    //constructors

    public User(){

    }

    public User(int id, String firstname, String lastname, String email, Float work_ex, String status, String max_education, String skills) {
        super();
        this.userId = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.work_ex = work_ex;
        this.max_education = max_education;
        this.status = status;
        this.skills = skills;
    }

    //public getter methods

    public int getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public String getSelf_intro() {
        return self_intro;
    }

    public Float getWork_ex() {
        return work_ex;
    }

    public String getMax_education() {
        return max_education;
    }

    public String getStatus() {
        return status;
    }

    public String getSkills() {
        return skills;
    }

    //public setter methods

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setSelf_intro(String self_intro) {
        this.self_intro = self_intro;
    }

    public void setWork_ex(Float work_ex) {
        this.work_ex = work_ex;
    }

    public void setMax_education(String max_education) {
        this.max_education = max_education;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }


}