package JobPortal.model;

import javax.persistence.*;

/**
 * Created by anvita on 5/13/17.
 */

@Entity
@Table(name="interview")
public class Interview {
  @Id
  @GeneratedValue(strategy= GenerationType.AUTO)
  @Column(name = "interviewId")
  private int interviewId;

  @Column
  private int userId;

  @Column
  private int jobId;

  @Column
  private int applicationId;

  @Column
  private String status;

  @Column
  private String location;

  @Column
  private String time;

  @Column
  private String feedback;

  public Interview() {

  }

  public Interview(int userId, int jobId, int applicationId, String location, String time, String status) {
    this.userId = userId;
    this.jobId = jobId;
    this.applicationId = applicationId;
    this.location = location;
    this.time = time;
    this.status = status;
  }

}
