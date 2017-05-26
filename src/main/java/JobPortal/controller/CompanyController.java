package JobPortal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import JobPortal.model.User;
import JobPortal.service.UserService;
import JobPortal.model.Company;
import JobPortal.service.CompanyService;
import JobPortal.model.JobOpening;
import JobPortal.service.JobOpeningService;
import JobPortal.model.Interview;
import JobPortal.exception.HttpError;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by ipshamohanty on 5/1/17.
 */

@Controller
public class CompanyController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CompanyService companyService;
    
    @Autowired 
    private JobOpeningService jobOpeningService;

    @Autowired
    private UserService userService;


    @RequestMapping( value = "/company/{companyId}", method = RequestMethod.GET)
    public ResponseEntity getCompany(HttpServletResponse response, 
                                @PathVariable int companyId) {

        Company company = companyService.getCompany(companyId);

        if (null == company) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested company with id " + companyId + " does not exist").
            toString());

        }
        List<JobOpening> jobOpeningList = new ArrayList<>();
        jobOpeningList = jobOpeningService.getJobOpeningsInCompany(String.valueOf(companyId));
        int no_of_openings = jobOpeningList.size(); 
        return new ResponseEntity<>(companyService.getCompany(company, no_of_openings), 
                        new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value= "/company", method = RequestMethod.POST)
    public ResponseEntity createCompany(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params)
    {
        if (params.get("companyName") == null || params.get("website") == null 
                             || params.get("description") == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested details company name or website or description does not exist").
            toString());

        }         
        String companyName = params.get("companyName");
        String website = params.get("website");
        String description = params.get("description");
        String location = params.get("location");
        String logoImageUrl = params.get("logoImageUrl");
        String password = params.get("password");
        String companyEmail = params.get("companyEmail");

        if (userService.getUser(companyEmail) != null || 
                        companyService.getCompany(companyEmail) != null ) {
           log.error("email already exists");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpError(400,
            "Sorry the email address is already registered").toString());
        }

        
        Company company = companyService.createCompany(companyName, 
                        website, location,logoImageUrl, description, password, companyEmail);
        /* 
        ModelMap model = new ModelMap();
        model.addAttribute("companyId", company.getCompanyId());
        model.addAttribute("companyname", company.getCompanyname());
        model.addAttribute("website", company.getWebsite());
        model.addAttribute("location", company.getLocation());
        model.addAttribute("logo_image_URL", company.getLogo_image_URL());
        model.addAttribute("description", company.getDescription());
        */
        try {
            return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpError(400,
                    e.getMessage()).toString());
 
        }
    }

    @RequestMapping(value= "/company/{companyId}", method = RequestMethod.PUT)
    public ResponseEntity updateCompany(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params,
                                    @PathVariable int companyId)
    {
        
        Company company = companyService.getCompany(companyId);

        if (null == company) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested company with id " + companyId + " does not exist").
            toString());

        }       
        String companyName = params.get("companyname") == null ?
                                company.getCompanyname() : params.get("companyname"); 
        String website = params.get("website") == null ?
                                company.getWebsite() : params.get("website");
        String description = params.get("description") == null ?
                                company.getDescription() : params.get("description");
        String location = params.get("location") == null ?
                                company.getLocation() : params.get("location");
        String logoImageUrl = params.get("logoImageUrl") == null ?
                                company.getLogo_image_url() : params.get("logoImageUrl");
        
        String password = params.get("password") == null ?
                company.getPassword() : params.get("password");

        company = companyService.updateCompany(companyName,  
                        website, location,logoImageUrl, description, password, companyId);
        return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);

    }
    
    @RequestMapping(value= "/company/check", method = RequestMethod.POST)
    public ResponseEntity checkCompany(@RequestParam String email) 
    {
        try {

            User userEmail = userService.getUser(email);
            Company companyEmail = companyService.getCompany(email);

            if (userEmail == null && companyEmail == null) {
                return new ResponseEntity<>(null, new HttpHeaders(), HttpStatus.OK);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpError(400,
                "email exists").
                toString());
            }

        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpError(500,
                "Error creating company").toString());
        }
       
    }

    @RequestMapping( value = "/company", method = RequestMethod.GET)
    public ResponseEntity getCompanyByEmail(@RequestParam String email,
                                            @RequestParam String password) 
    {

        Company company = companyService.getCompany(email);
        if (company != null) {
            if (company.getPassword().equals(password)) {
                return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);
            } else {
                 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpError(400,
                 "wrong credentials").toString());            
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
                   "Company does not exist").toString());          

        }

    }

   @RequestMapping( value = "/company/interview", method = RequestMethod.POST)
    public ResponseEntity createInterviewInvite(@RequestParam String userId,
                                                @RequestParam String jobId,
                                                @RequestParam String applicationId,
                                                @RequestParam String location,
                                                @RequestParam String start,
                                                @RequestParam String end)
    {

        int userid = Integer.valueOf(userId);
        int jobid = Integer.valueOf(jobId);
        int applicationid = Integer.valueOf(applicationId);

        User user = userService.getUserByID(userid);
        JobOpening jobOpening = jobOpeningService.getJobOpeningByJobId(jobId);
        
        if (null == user || null == jobOpening) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
                    "User does not exist").toString());
        }

       String userEmail = user.getEmail();  
       String title = jobOpening.getTitle();
       
        
       String interviewResult = companyService.scheduleInterview(userid, jobid, applicationid, 
                            title, userEmail, location, start, end); 
       //Interview interview = companyService.scheduleInterview(userid, jobid, "Analyst", 
       //                             "sandhya.ramanarayanan@sjsu.edu", location, start, end); 
       
      if (null == interviewResult) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpError(500,
                 "Server error. Please try again").toString()); 
       }
       return new ResponseEntity<>(interviewResult, new HttpHeaders(), HttpStatus.OK);
 
       
//        companyService.scheduleInterview(userid, jobid, applicationid, title,
//                userEmail, location, start, end);


    }

    @RequestMapping( value = "/company/interview", method = RequestMethod.GET)
    public ResponseEntity searchInterviewByStatus(String statuslist)
    {
            List<Interview> result= companyService.getInterviewByStatus(statuslist);
            if ( null == result) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpError(500,
                 "Server error. Please try again").toString()); 
                
            }

            return new ResponseEntity<>(result, new HttpHeaders(), HttpStatus.OK);
    }

     

}
