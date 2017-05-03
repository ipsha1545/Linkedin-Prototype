package JobPortal.Dao;


import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

import JobPortal.model.JobOpenings;

@Transactional
public interface JobOpeningsDao extends CrudRepository<JobOpenings, Integer> {

}
