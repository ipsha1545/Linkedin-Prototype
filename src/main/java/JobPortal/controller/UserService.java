package JobPortal.controller;

import JobPortal.model.User;
import JobPortal.model.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

/**
 * Created by anvita on 4/28/17.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserService(){

    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(int id){

        User user = null;

        return userDao.findByuserId(id);
    }
    public void createUser(String firstname, String lastname, String email, String image, String intro,
                           Float workex, String status, String max_education, String skills){


        ModelAndView modelAndView;
        ObjectMapper mapper = new ObjectMapper();
        ModelMap model = new ModelMap();

        User user;

        try{

            System.out.println("received user details in user service");

            int userId = userDao.getId() + 1;
          //  System.out.println("Id is "+userDao.getId());

            user = new User(userId, firstname, lastname, email, workex, status, max_education, skills);
            System.out.println("reached 1");

            userDao.save(user);
            System.out.println("reached");


        }catch(Exception ex){
            String message = "Another user with the same number already exists.";

        }

    }



}
