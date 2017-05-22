package JobPortal.Dao;

import javax.transaction.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import JobPortal.model.Interview;

/**
 * Created by anvita on 4/18/17.
 */

@Transactional
public interface InterviewDao extends CrudRepository<Interview, Integer> {


}
