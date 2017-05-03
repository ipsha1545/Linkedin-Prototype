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
import java.util.Random;

/**
 * Created by anvita on 4/28/17.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserService() {

    }

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User getUser(String email) {
        return userDao.findUserByEmail(email);
    }

    public User getUserByID(int id) {
        return userDao.findByuserId(id);
    }


    public ResponseEntity createUser(String firstname, String lastname, String phone, String email, String password,
                                             String address, String education, String skills, Float experience,
                                             String introduction, String status, String image) {

        User user;

        try {
            User userPhone = userDao.findUserByPhone(phone);
            User userEmail = userDao.findUserByEmail(email);

            System.out.println("request recieved in user service");

            if (userEmail == null) {
                if (userPhone == null) {

                    Random rand = new Random();
                    String random_verification =
                            "" + (rand.nextInt(9) + 1) + "" + (rand.nextInt(9) + 1) + ""
                                    + (rand.nextInt(9) + 1) + "" + (rand.nextInt(9) + 1) + "" + (rand.nextInt(9) + 1);
                    System.out.println(random_verification);
                    int verification = Integer.valueOf(random_verification);

                    user = new User(firstname, lastname, phone, email, password, address, education, skills, experience, introduction, status, image, verification);
                    userDao.save(user);

                    ModelMap model = new ModelMap();
                    model.addAttribute("code", 200);
                    model.addAttribute("user", user);;
                    return ResponseEntity.ok(model);

                } else {
                    ModelMap model = new ModelMap();
                    model.addAttribute("code", 400);
                    model.addAttribute("msg", "Phone number already exists");
                    return ResponseEntity.ok(model);
                }
            } else {
                ModelMap model = new ModelMap();
                model.addAttribute("code", 400);
                model.addAttribute("msg", "Email already exists");
                return ResponseEntity.ok(model);
            }
        } catch (Exception ex) {
            ModelMap model = new ModelMap();
            model.addAttribute("code", 400);
            model.addAttribute("msg", "Error creating user");
            return ResponseEntity.ok(model);
        }
    }


    public ResponseEntity<Object> updateUser(int id, String parameter, String value) {

        ModelMap model = new ModelMap();

        User user = userDao.findByuserId(id);

        if(user != null){

            try{
                if(parameter.equals("firstname")){
                    user.setFirstname(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("lastname")){
                    user.setLastname(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("address")){
                    user.setAddress(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("education")){
                    user.setEducation(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("introduction")){
                    user.setIntroduction(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("phone")){
                    user.setPhone(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("skills")){
                    user.setSkills(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("status")){
                    user.setStatus(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

                if(parameter.equals("verified")){
                    user.setVerified(Integer.valueOf(value));
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return ResponseEntity.ok(model);
                }

            }catch(Exception e){
                model.addAttribute("statuscode",400);
                return ResponseEntity.ok(model);
            }

        }

return  null;

    }

}