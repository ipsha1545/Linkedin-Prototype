package JobPortal.model;

import javax.persistence.*;
import java.util.*;

import JobPortal.controller.*;
import java.util.Random;
//import JobPortal.service.*;

/**
 * Created by ipsha on 4/18/17.
 */
@Entity
@Table(name = "users")
public class User {

    //private attributes
   ////firstname, lastname, phone, email, address, education, skills, experience, introduction, status, image
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="userId")
    private int userId;

    @Column(name="firstname")
    private String firstname;

    @Column(name="lastname")
    private String lastname;

    @Column(name="phone",unique = true)
    private String phone;

    @Column(name="email", unique = true)
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="image")
    private String image;

    @Column(name="address")
    private String address;

    @Column(name="introduction")
    private String introduction;

    @Column(name="experience")
    private Float experience;

    @Column(name="education")
    private String education;

    @Column(name="current_status")
    private String status;

    @Column(name="skills")
    private String skills;

    @Column(name="verified",columnDefinition="Decimal(10,0) default '0'")
    private int verified;

    @Column(name="pending_applications",columnDefinition="Decimal(10,0) default '0'")
    private int pending_applications;

    @Column(name="verification_code")
    private int verification_code;

    //constructors

    public User(){

    }

    public User(String firstname, String lastname,String phone, String email,String password, String address,
                String education,String skills, Float experience, String introduction, String status,
                String image, int verification) {
        super();

        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.address = address;
        this.experience = experience;
        this.education = education;
        this.introduction = introduction;
        this.status = status;
        this.skills = skills;
        this.image = image;
        this.verification_code =  verification;

    }

    //public getter methods

    public int getUserId() {
        return userId;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPending_applications() {
        return pending_applications;
    }

    public void setPending_applications(int pending_applications) {
        this.pending_applications = pending_applications;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getIntroduction() {
        return introduction;
    }

    public Float getExperience() {
        return experience;
    }

    public String getEducation() {
        return education;
    }

    public String getStatus() {
        return status;
    }

    public String getSkills() {
        return skills;
    }

    public int getVerified() {
        return verified;
    }

    public String getPassword() {
        return password;
    }

    public int getVerification_code() {
        return verification_code;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setExperience(Float experience) {
        this.experience = experience;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setVerification_code(int verification_code) {
        this.verification_code = verification_code;
    }

}
