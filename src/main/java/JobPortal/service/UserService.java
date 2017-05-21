package JobPortal.service;

import JobPortal.Dao.CompanyDao;
import JobPortal.Dao.JobOpeningDao;
import JobPortal.Dao.JobOpening_UserDao;
import JobPortal.Dao.UserDao;
import JobPortal.model.Company;
import JobPortal.model.JobOpening_User;
import JobPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by anvita on 4/28/17.
 */

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private JobOpeningDao jobOpeningDao;

    @Autowired
    private JobOpening_UserDao jobOpening_userDao;

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

    public ResponseEntity checkUser(String email, String phone){

        try {
            User userEmail = userDao.findUserByEmail(email);
            User userPhone = userDao.findUserByPhone(phone);
            Company companyEmail = companyDao.findByCompanyEmail(email);


            ModelMap model = new ModelMap();

            if (userEmail == null && companyEmail == null) {
                if(userPhone == null){
                    model.addAttribute("code", 200);;
                    return new ResponseEntity(model, HttpStatus.OK);
                }else{
                    model.addAttribute("code", 400);
                    model.addAttribute("msg", "phone number exists");
                    return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
                }
            } else {
                model.addAttribute("code", 400);
                model.addAttribute("msg", "email exists");
                return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception ex) {
            ModelMap model = new ModelMap();
            model.addAttribute("code", 400);
            model.addAttribute("msg", "Error creating user");
            return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity createUser(String firstname, String lastname, String phone, String email, String password,
                                     String address, String education, String skills, Float experience,
                                     String introduction, String status, String image) {

        User user;

        try {
            User userPhone = userDao.findUserByPhone(phone);
            User userEmail = userDao.findUserByEmail(email);

            if (userEmail == null) {
                if (userPhone == null) {

                    user = new User(firstname, lastname, phone, email, password, address, education, skills, experience, introduction, status, image);
                    userDao.save(user);

                    ModelMap model = new ModelMap();
                    model.addAttribute("code", 200);
                    model.addAttribute("user", user);;
                    return new ResponseEntity(model, HttpStatus.OK);

                } else {
                    ModelMap model = new ModelMap();
                    model.addAttribute("code", 400);
                    model.addAttribute("msg", "Phone number already exists");
                    return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
                }
            } else {
                ModelMap model = new ModelMap();
                model.addAttribute("code", 400);
                model.addAttribute("msg", "Email already exists");
                return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ModelMap model = new ModelMap();
            model.addAttribute("code", 400);
            model.addAttribute("msg", "Error creating user");
            return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
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
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("lastname")){
                    user.setLastname(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("address")){
                    user.setAddress(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("education")){
                    user.setEducation(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("introduction")){
                    user.setIntroduction(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("phone")){
                    user.setPhone(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("skills")){
                    user.setSkills(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("status")){
                    user.setStatus(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("password")){
                    user.setPassword(value);
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

                if(parameter.equals("experience")){
                    user.setExperience(Float.valueOf(value));
                    userDao.save(user);

                    model.addAttribute("statuscode",200);
                    return new ResponseEntity(model, HttpStatus.OK);
                }

            }catch(Exception e){
                model.addAttribute("statuscode",400);
                return new ResponseEntity(model, HttpStatus.BAD_REQUEST);
            }

        }

        return  null;

    }

    public ResponseEntity addImage(int userid, String image) {

        User user = userDao.findByuserId(userid);
        try{

            if(user != null){

                user.setImage(image);
                userDao.save(user);
                return new ResponseEntity(HttpStatus.OK);

            }else{
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getUserInterviews(int userid) {

        try{
            List<JobOpening_User> interview_jobs = jobOpening_userDao.getUserInterviews(userid);

            Object obj[] = new Object[interview_jobs.size()];

            for(int i = 0; i < interview_jobs.size(); i++){
                ModelMap m = new ModelMap();
                m.addAttribute("applicationId", interview_jobs.get(i).getJob_userId());
                m.addAttribute("job", jobOpeningDao.findByJobId(interview_jobs.get(i).getJobId()));
                m.addAttribute("userId", interview_jobs.get(i).getUserId());
                m.addAttribute("status", interview_jobs.get(i).getStatus());
                m.addAttribute("interested", interview_jobs.get(i).isInterested());
                m.addAttribute("terminal", interview_jobs.get(i).isTerminal());
                m.addAttribute("resume", interview_jobs.get(i).getResume());
                obj[i] = m;
            }

            return new ResponseEntity(obj, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }


    public static byte[] readBytesFromFile(String filePath) throws IOException {

        try { File inputFile = new File(filePath);
            FileInputStream inputStream = new FileInputStream(inputFile);

            byte[] fileBytes = new byte[(int) inputFile.length()];
            inputStream.read(fileBytes);
            inputStream.close();

            return fileBytes;
        } catch (Exception e) {
            return null;
        }
    }

}