package JobPortal.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by ipshamohanty on 5/9/17.
 */
public class job_openings_user {


    @Id
    private int jobId;

    @Column(name = "userId")
    private int userId;


//The status will be either pending or applied
    @Column(name = "status")
    private String status;


//Terminal status will be either 0-rejected 1-given offer
    @Column(name = "terminal_status")
    private boolean description;

    @Column(name = "responsibilities")
    private String responsibilities;


    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public job_openings_user(int jobId, int userId, String status, boolean description, String responsibilities) {
        this.jobId = jobId;
        this.userId = userId;
        this.status = status;
        this.description = description;
        this.responsibilities = responsibilities;
    }
}



