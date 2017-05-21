package JobPortal.controller;

import JobPortal.service.CompanyService;
import JobPortal.service.JobOpeningService;
import JobPortal.service.JobOpening_UserService;
import JobPortal.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created by anvita on 5/13/17.
 */

@Controller
public class JobOpening_UserController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private UserService userService;

    @Autowired
    private JobOpeningService jobOpeningService;

    @Autowired
    private JobOpening_UserService jobOpening_userService;

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value ="/userJob/interest", method = RequestMethod.POST)
    public ResponseEntity markInterested(@RequestParam Map<String,String> params) {

        int userId = Integer.valueOf(params.get("userId"));
        int jobId = Integer.valueOf(params.get("jobId"));
        boolean markedInterest =  jobOpening_userService.markInterested(userId, jobId);

        if(markedInterest){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return null;
        }

    }

    @RequestMapping(value ="/userJob/notInterest", method = RequestMethod.POST)
    public ResponseEntity markNotInterested(@RequestParam Map<String,String> params) {

        int userId = Integer.valueOf(params.get("userId"));
        int jobId = Integer.valueOf(params.get("jobId"));

        boolean markedNotInterest =  jobOpening_userService.markNotInterested(userId, jobId);

        if(markedNotInterest){
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }


    @RequestMapping(value ="/userJob/applyResume", method = RequestMethod.POST)
    public void user_applyResume() {

        //System.out.println("apply resume here");


    }

    @RequestMapping(value ="/company/getApplication", method = RequestMethod.GET)
    public ResponseEntity getCompanyApplications(@RequestParam(value="jobid") String jobid) {

        try{

            return jobOpening_userService.getCompanyApplications(Integer.valueOf(jobid));

        }catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value ="/users/getInterestedJobs/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserAllInterestedJobs(@PathVariable(value="id") String userid) {

        return jobOpening_userService.getUserAllInterestedJobs(Integer.valueOf(userid));


    }

    @RequestMapping(value ="/userJob/interestedJobs/{id}", method = RequestMethod.GET)
    public ResponseEntity getUser_interestedJobs(@PathVariable(value="id") String id) {

        try{
            Object obj = jobOpening_userService.getUserJobStatus(Integer.valueOf(id));

            ModelMap m = new ModelMap();
            m.addAttribute("jobStatus", obj);
            m.addAttribute("interests", jobOpening_userService.getUserInterestJobs(Integer.valueOf(id)));

            return new ResponseEntity(m, HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value ="/userJob/apply", method = RequestMethod.POST)
    public ResponseEntity apply_Job(@RequestParam Map<String,String> params) {

        String resume;

        if(params.get("resume") != null){
            resume = params.get("resume");

        }else{
            resume = null;
        }


        return jobOpening_userService.apply_Job(Integer.valueOf(params.get("userid")), Integer.valueOf(params.get("jobid")), resume);
    }

    @RequestMapping(value ="/userJob/changeStatus", method = RequestMethod.POST)
    public ResponseEntity changeStatus(@RequestParam Map<String,String> params) {

        int applicationId = Integer.valueOf(params.get("applicationId"));
        String status = params.get("status");

       // return jobOpening_userService.changeStatus(applicationId, status);
        return jobOpening_userService.changeStatus(applicationId, status);
    }


}
