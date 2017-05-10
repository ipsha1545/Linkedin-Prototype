package JobPortal.Dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;
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

    
}
