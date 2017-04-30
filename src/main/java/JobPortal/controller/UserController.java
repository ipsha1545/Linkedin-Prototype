package JobPortal.controller;

import java.util.List;

import org.json.*;
import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;

import JobPortal.model.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import org.springframework.http.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value= "/user/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getById(HttpServletResponse response, @PathVariable(value="id") int id) {


        User user;

        ModelAndView modelAndView = null;
        ModelMap model = new ModelMap();


            user = userService.getUser(id);

            model.addAttribute("id", user.getUserId());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("email", user.getEmail());
        model.addAttribute("max education", user.getMax_education());
        model.addAttribute("profile image", user.getProfile_image());
        model.addAttribute("intro", user.getSelf_intro());
        model.addAttribute("skills", user.getSkills());
        model.addAttribute("status", user.getStatus());
        model.addAttribute("work ex", user.getWork_ex());

        return ResponseEntity.ok(model);


    }


    @RequestMapping(value= "/user", method = RequestMethod.POST)
    public void create(HttpServletResponse response,@RequestParam(value="firstname", required = true) String firstname,
                               @RequestParam(value="lastname", required = true) String lastname,
                               @RequestParam(value="email", required = true) String email,
                               @RequestParam(value="image", required = false) String image,
                               @RequestParam(value="intro", required = false) String intro,
                               @RequestParam(value="workex", required = true) Float workex,
                               @RequestParam(value="status", required = true) String status,
                               @RequestParam(value="max_education", required = true) String max_education,
                               @RequestParam(value="skills", required = true) String skills
                       ) throws IOException {

            userService.createUser(firstname, lastname, email, image, intro, workex,
                    status, max_education, skills);

    }




}