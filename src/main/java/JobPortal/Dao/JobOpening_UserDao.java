package JobPortal.Dao;

import JobPortal.model.JobOpening_User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by anvita on 5/13/17.
 */

@Transactional
public interface JobOpening_UserDao extends CrudRepository<JobOpening_User, Integer> {

    @Query(value = "select * from jobportal.job_openings_user where job_userId = ?", nativeQuery = true)
    JobOpening_User findByJob_userId(int job_userId);

    @Query(value = "select * from jobportal.job_openings_user where userId = :userId AND jobId = :jobId", nativeQuery = true)
    JobOpening_User checkEntry(@Param("userId") int userId, @Param("jobId") int jobId);

    @Query(value = "select distinct jobId from jobportal.job_openings_user where interested = 1 and userId = ?", nativeQuery = true)
    List<Integer> getUserInterestJobs(@Param("userId") int userId);


    @Query(value = "select * from jobportal.job_openings_user where userId = ?", nativeQuery = true)
    List<JobOpening_User> getUserJobStatus(@Param("userId") int userId);

    @Query(value = "select * from jobportal.job_openings_user where jobId = ?", nativeQuery = true)
    List<JobOpening_User> getCompanyApplication(int jobId);

    @Query(value = "select * from jobportal.job_openings_user where userId = ?", nativeQuery = true)
    List<JobOpening_User> getInterestedJobs(int userid);

    @Query(value = "select * from jobportal.job_openings_user where jobId = ? AND terminal = 0", nativeQuery = true)
    List<JobOpening_User> getActiveCompanyApplications(int jobId);


@Query(value = "select * from jobportal.job_openings_user where (status like '%interview%' or status like '%Offer%') and userId = ?", nativeQuery = true)
//    List<JobOpening_User> getUserInterviews(int userid);

    @Query(value = "select * from jobportal.job_openings_user where (status not IN ('Applied')) and userId = ?", nativeQuery = true)
    List<JobOpening_User> getUserInterviews(int userid);

    @Query(value = "select * from jobportal.job_openings_user where status = 'OfferAccepted' and jobId = ?", nativeQuery = true)
    List<JobOpening_User> getOfferJobs(int jobid);

    @Query(value = "select * from jobportal.job_openings_user where (status = 'Applied' or status like '%Interview%' or status = 'offered') and jobId = ?", nativeQuery = true)
    List<JobOpening_User> getNonTerminalApplications(int jobid);


}
