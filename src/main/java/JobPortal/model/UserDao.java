package JobPortal.model;

import javax.transaction.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import JobPortal.model.User;

/**
 * Created by anvita on 4/18/17.
 */

@Transactional
public interface UserDao extends CrudRepository<User, Integer> {

    /**
     * This method will find an User instance in the database by its email.
     * Note that this method is not implemented and its working code will be
     * automagically generated from its signature by Spring Data JPA.
     */
    public User findByuserId(int id);

    @Query(value = "select max(user_id) from jobportal.user", nativeQuery = true)
    int getId();


}