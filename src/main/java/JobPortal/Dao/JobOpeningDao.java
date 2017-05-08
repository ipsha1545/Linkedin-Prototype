package JobPortal.Dao;


import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

import JobPortal.model.JobOpening;

@Transactional
public interface JobOpeningDao extends CrudRepository<JobOpening, Integer> {
    
    public JobOpening findByJobId(int jobId);

}
