package JobPortal.controller;

import JobPortal.model.User;
import JobPortal.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value= "/users", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity getById(HttpServletResponse response,
                                          @RequestParam(value="email", required = true) String email,
                                          @RequestParam(value="password", required = true) String password) {


        User user = userService.getUser(email);

        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);

    }


    @RequestMapping(value= "/users/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getById(HttpServletResponse response, @PathVariable(value="id") String id) {


        User user;
        ModelMap model = new ModelMap();

        user = userService.getUserByID(Integer.valueOf(id));

        return ResponseEntity.ok(user);

    }

    @RequestMapping(value= "/users", method = RequestMethod.POST)
    public ResponseEntity create(HttpServletResponse response,@RequestParam(value="firstname", required = true) String firstname,
                                         @RequestParam(value="lastname", required = true) String lastname,
                                         @RequestParam(value="email", required = true) String email,
                                         @RequestParam(value="password", required = true) String password,
                                         @RequestParam(value="image", required = false) String image,
                                         @RequestParam(value="introduction", required = false) String introduction,
                                         @RequestParam(value="experience", required = true) Float experience,
                                         @RequestParam(value="status", required = true) String status,
                                         @RequestParam(value="education", required = true) String education,
                                         @RequestParam(value="skills", required = true) String skills,
                                         @RequestParam(value="address", required = true) String address,
                                         @RequestParam(value="phone", required = true) String phone


    ) throws IOException {

        return userService.createUser(firstname, lastname, phone, email, password, address, education, skills, experience, introduction, status, image);
    }

    @RequestMapping(value= "/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> update(HttpServletResponse response,
                                         @PathVariable(value="id") String id,
                                         @RequestParam Map<String,String> params


    ) throws IOException {

        ResponseEntity<Object> rs = null;

        if(params.get("firstname") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "firstname", params.get("firstname"));
        }

        if(params.get("lastname") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "lastname", params.get("lastname"));
        }

        if(params.get("address") !=null){

            rs = userService.updateUser(Integer.valueOf(id), "address", params.get("address"));
        }

        if(params.get("education") !=null){

            rs = userService.updateUser(Integer.valueOf(id), "education", params.get("education"));
        }

        if(params.get("introduction") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "introduction", params.get("introduction"));
        }

        if(params.get("phone") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "phone", params.get("phone"));
        }

        if(params.get("skills") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "skills", params.get("skills"));
        }

        if(params.get("status") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "skills", params.get("status"));
        }

        if(params.get("verified") !=null){
            rs = userService.updateUser(Integer.valueOf(id), "verified", params.get("verified"));
        }

        return rs;
    }



}