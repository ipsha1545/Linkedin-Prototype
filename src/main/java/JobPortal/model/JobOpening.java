package JobPortal.model;

import javax.persistence.*;

@Entity
@Table(name="job_openings")
public class JobOpening {
    
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "jobId")
    private int jobId;
    
    @Column(name = "companyId")
    private int companyId;

    @Column(name = "title")
    private String title;
    
    @Column(name = "description")
    private String description;

    @Column(name = "responsibilities")
    private String responsibilities;

    @Column(name = "location")
    private String location;

    @Column(name = "salary")
    private int salary;

    @Column(name = "companyname")
    private String companyname;
   
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    } 

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCompanyname() {
        this.companyname = companyname;
    }
    
    public String getCompanyname() {
           return companyname;
    }


    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

   public JobOpening(int companyId, String companyName, String title, String description,
                     String responsibilities, String location, int salary)
   {
        this.companyId = companyId;
        this.title = title;
        this.description = description;
        this.responsibilities = responsibilities;
        this.location = location;
        this.salary = salary;
        this.companyname = companyname;
   } 
}
