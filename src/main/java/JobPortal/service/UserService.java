package JobPortal.service;

import JobPortal.model.User;
import JobPortal.Dao.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.ResponseEntity;
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
        return userDao.findByuserId(id);
    }


    public ResponseEntity<Object> createUser(String firstname, String lastname, String phone, String email, String address, String education, String skills,
                                             Float experience, String introduction, String status, String image){

        ModelMap model = new ModelMap();
        int id;

        User user;

        try{
            User userPhone = userDao.findUserByPhone(phone);
            User userEmail = userDao.findUserByEmail(email);

            System.out.println("request recieved in user service");

            if(userEmail == null){
                if(userPhone == null){
                    user = new User(firstname, lastname, phone, email, address, education, skills,experience, introduction, status, image);
                    userDao.save(user);

                    id = user.getUserId();
                    model.addAttribute("user", id);

                    return  ResponseEntity.ok(model);

                }else{
                    System.out.println("phone exists - creating user");
                    model.addAttribute("user", -1);
                    return  ResponseEntity.ok(model);
                }
            }else{
                model.addAttribute("user", 0);
                System.out.println("email exists - creating user");
                return  ResponseEntity.ok(model);
            }
        }catch(Exception ex){
            System.out.println("some unknown error creating user");
            model.addAttribute("user", -2);
            return  ResponseEntity.ok(model);
        }
    }


}
