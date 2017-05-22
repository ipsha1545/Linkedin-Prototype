package JobPortal.Dao;

import javax.transaction.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;

import JobPortal.model.Interview;

import java.util.List;

/**
 * Created by anvita on 4/18/17.
 */

@Transactional
public interface InterviewDao extends CrudRepository<Interview, Integer> {

    @Query(nativeQuery = true, value = "select * from jobportal.interview where status IN :statuslist")
    public List<Interview> getInterviewByStatus(@Param("statuslist") List<String> statuslist);
}
