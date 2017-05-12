package JobPortal.repository;

import org.springframework.data.repository.CrudRepository;

import JobPortal.model.JobOpening;
import JobPortal.model.User;


public interface UserRepository extends CrudRepository<User,Integer>{


}
