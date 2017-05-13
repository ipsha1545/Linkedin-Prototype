package JobPortal.Dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;

import JobPortal.model.JobOpening;
import java.util.List;

@Transactional
public interface JobOpeningDao extends CrudRepository<JobOpening, Integer> {
    
    public JobOpening findByJobId(int jobId);
    
    @Query(nativeQuery = true, value = "select * from jobportal.job_openings where companyid = ?")
    public List<JobOpening> findJobOpeningsInCompany(String companyid);

    @Query(nativeQuery = true, value = "select * from jobportal.job_openings where jobId = ?")
    public JobOpening findJobOpeningByJobId(int jobId);

    @Query(nativeQuery = true, value = "select * from jobportal.job_openings where status IN :statuslist AND companyid = :companyid")
    public  List<JobOpening> findJobOpeningsInCompanyByStatus(@Param("companyid") String companyid, @Param("statuslist") List<String> statuslist);

    @Query(nativeQuery = true, value = "select jobId from jobportal.job_openings where companyname IN :companies AND status = 'Open'")
    public  List<Integer> findJobOpeningsInCompanyByName(@Param("companies") List<String> companies);
    
    @Query(nativeQuery = true, value = "select jobId from jobportal.job_openings where location IN :locations AND status = 'Open'")
    public  List<Integer> findJobOpeningsInCompanyByLocation(@Param("locations") List<String> locations);
    
    
    
    @Query(nativeQuery = true, value = "select jobId from jobportal.job_openings where salary > :salarystart AND salary < :salaryend AND status = 'Open'")
    public  List<Integer> findJobOpeningsInCompanyBySalary(@Param("salarystart") int salarystart, @Param("salaryend") int salaryend);
    

    @Query(nativeQuery = true, value = "select * from jobportal.job_openings where jobId IN :jobIds")
    public  List<JobOpening> findJobOpeningsInCompanyByFilter(@Param("jobIds") List<Integer> jobIds);

    @Query(value = "select * from jobportal.job_openings where status = 'Open'", nativeQuery = true)
    List<JobOpening> getAllJobs();

    @Query(value = "select distinct location from jobportal.job_openings", nativeQuery = true)
    List<String> getAllLocations();

    
}
