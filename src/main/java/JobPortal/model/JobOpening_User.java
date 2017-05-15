package JobPortal.model;

import javax.persistence.*;

/**
 * Created by anvita on 5/13/17.
 */

@Entity
@Table(name="job_openings_user")
public class JobOpening_User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "job_userId")
    private int job_userId;

    @Column(name = "userId")
    private int userId;

    @Column(name = "jobId")
    private int jobId;

    @Column(name = "companyId")
    private int companyId;

    @Column(name = "status")
    private String status;

    @Column(name = "interested", columnDefinition = "BIT(1)")
    private boolean interested;

    @Column(name = "terminal", columnDefinition = "BIT(1)")
    private boolean terminal;

    public String getResume() {
        return resume;
    }



    public JobOpening_User(){

    }

    public JobOpening_User(int userId, int jobId, int companyId, String status, boolean interested, boolean terminal) {
        this.userId = userId;
        this.jobId = jobId;
        this.companyId = companyId;
        this.status = status;
        this.interested = interested;
        this.terminal = terminal;
    }

    public int getJob_userId() {
        return job_userId;
    }

    public void setJob_userId(int job_userId) {
        this.job_userId = job_userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isInterested() {
        return interested;
    }

    public void setInterested(boolean interested) {
        this.interested = interested;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    @Column(name = "resume")
    private String resume;

}
