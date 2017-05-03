package JobPortal.Dao;

import javax.transaction.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import JobPortal.model.User;

/**
 * Created by anvita on 4/18/17.
 */

@Transactional
public interface UserDao extends CrudRepository<User, Integer> {


    public User findByuserId(int id);

    @Query(value = "select * from jobportal.users where phone = ?", nativeQuery = true)
    User findUserByPhone(String phone);

    @Query(value = "select * from jobportal.users where email = ?", nativeQuery = true)
    User findUserByEmail(String email);

}