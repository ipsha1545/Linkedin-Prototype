package JobPortal.model;

import javax.persistence.*;

/**
 * Created by ipshamohanty on 5/1/17.
 */
@Entity
@Table(name = "company")
public class Company {
    //private attributes
    ////companyId,companyname,website,location,logo_image_URL,description
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="companyId")
    private int companyId;

    @Column(name="companyname")
    private String companyname;

    @Column(name="website")
    private String website;

    @Column(name="location")
    private String location;

    @Column(name="logo_image_URL")
    private String Logo_image_URL;

    @Column(name="description")
    private String description;

    //constructors


    public Company(String companyname, String website, String location, String logo_image_URL, String description) {
        this.companyname = companyname;
        this.website = website;
        this.location = location;
        Logo_image_URL = logo_image_URL;
        this.description = description;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogo_image_URL() {
        return Logo_image_URL;
    }

    public void setLogo_image_URL(String logo_image_URL) {
        Logo_image_URL = logo_image_URL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Company(){

    }

}
