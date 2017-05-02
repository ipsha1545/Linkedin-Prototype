package JobPortal.controller;

import JobPortal.model.User;
import JobPortal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value= "/users/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getById(HttpServletResponse response, @PathVariable(value="id") int id) {


        User user;

        ModelAndView modelAndView = null;
        ModelMap model = new ModelMap();


            user = userService.getUser(id);

        model.addAttribute("id", user.getUserId());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("education", user.getEducation());
        model.addAttribute("image", user.getImage());
        model.addAttribute("introduction", user.getIntroduction());
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("status", user.getStatus());
        model.addAttribute("experience", user.getExperience());

        return ResponseEntity.ok(model);


    }


    @RequestMapping(value= "/users", method = RequestMethod.POST)
    public ResponseEntity<Object> create(HttpServletResponse response,@RequestParam(value="firstname", required = true) String firstname,
                                         @RequestParam(value="lastname", required = true) String lastname,
                                         @RequestParam(value="email", required = true) String email,
                                         @RequestParam(value="image", required = false) String image,
                                         @RequestParam(value="introduction", required = false) String introduction,
                                         @RequestParam(value="experience", required = true) Float experience,
                                         @RequestParam(value="status", required = true) String status,
                                         @RequestParam(value="education", required = true) String education,
                                         @RequestParam(value="skills", required = true) String skills,
                                         @RequestParam(value="address", required = true) String address,
                                         @RequestParam(value="phone", required = true) String phone


    ) throws IOException {

        ResponseEntity<Object> rs = userService.createUser(firstname, lastname, phone, email, address, education, skills, experience, introduction, status, image);

        return rs;

    }



}