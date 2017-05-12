package JobPortal.controller;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import java.io.IOException;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import JobPortal.model.Company;
import JobPortal.service.CompanyService;
import JobPortal.model.JobOpening;
import JobPortal.service.JobOpeningService;
import JobPortal.exception.HttpError;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class JobOpeningController {

    @Autowired
    private CompanyService companyService;
    
    @Autowired 
    private JobOpeningService jobOpeningService;
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value ="/jobopenings", method = RequestMethod.POST)
    public ResponseEntity createJobOpening(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params) 
    {
        String companyId = params.get("companyId");
        
        if (companyId == null) 
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested company with id " + companyId + " does not exist").
            toString());
        }

        Company company = companyService.getCompany(Integer.valueOf(companyId));
        
        if (company == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested company with id " + companyId + " does not exist").
            toString());
        }
        
        String title = params.get("title");
        String description = params.get("description");
        String location = params.get("location");
        String salary = params.get("salary");
        String responsibilties = params.get("responsibilities");
      
        JobOpening jobopening = jobOpeningService.createJobOpening(company, title, description, 
                                    responsibilties, location, salary);
        if (jobopening == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new HttpError(500,
            "Server error, please try again").toString());
        } 
        return new ResponseEntity<>(companyService.getJobopeningInCompany(company,jobopening), 
                                            new HttpHeaders(), HttpStatus.OK);

    }
    
    //Creating job openings by giving job id.Need to remove the auto-generated tag of jobId 
    
     @RequestMapping(value="/JobOpening/{jobId}", method = RequestMethod.POST)
     public @ResponseBody ResponseEntity createJobOpeningforuser(@PathVariable("jobId")Integer jobId,
    		    							  @RequestParam(value="company_id") Integer company_id,
                                              @RequestParam(value="description") String description,
                                              @RequestParam(value="location") String location,
                                              @RequestParam(value="responsibilities")String  responsibilities,
                                              @RequestParam(value="salary")Integer salary,
                                              @RequestParam(value="status")String status,
                                              @RequestParam(value="title")String title
                                              ) {
    	 
    	// System.out.println(jobId + company_id + description);

         JobOpening p = jobOpeningRepository.findOne(jobId);
         
       //	 System.out.println(p);

         
         HashMap<String,List> abc = new HashMap<String,List>();
         HashMap<String,java.lang.Object> passengerlistobject = new HashMap<String,java.lang.Object>();
         
         try {

             p = jobOpeningRepository.save(new JobOpening(jobId, company_id, description,location, responsibilities, salary,status,title));
             System.out.println("p "+ p.getCompanyId()+ " " + p.getCompanyname()+ " " + p.getDescription());
             HashMap<String,java.lang.Object> passengerlistobject1 = new HashMap<String,java.lang.Object>();
             passengerlistobject1.put("jobOpening ",p);
             System.out.println("jobOpening "+passengerlistobject1);

              return ResponseEntity.ok(p);
               


         }catch(Exception ex){

        	 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
        	            "Sorry the requested jobopening cannot be created").
        	            toString());
         }



     @RequestMapping(value ="/company/{companyId}/jobopenings", method = RequestMethod.GET)
     public ResponseEntity getJobOpeningsInCompany( HttpServletResponse response, 
                                            @PathVariable String companyId,
                                            @RequestParam Map<String, String> params) 
    {
        Company company = companyService.getCompany(Integer.valueOf(companyId));
        
        if (company == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested company with id " + companyId + " does not exist").
            toString());
        }
        
        if (params.get("statuslists") != null) {
            String statuslists = params.get("statuslists");
            List<String> statusList= Arrays.asList(statuslists.split("\\s*,\\s*"));
            List<JobOpening> jobOpeningByStatusList = new ArrayList<>();
            jobOpeningByStatusList = jobOpeningService.
                                    getJobOpeningsInCompany(companyId, statusList);

            return new ResponseEntity<>(companyService.getJobOpenings(company, 
                                jobOpeningByStatusList), new HttpHeaders(), HttpStatus.OK);

        }
        List<JobOpening> jobOpeningList = new ArrayList<>();
        jobOpeningList = jobOpeningService.getJobOpeningsInCompany(companyId);
        return new ResponseEntity<>(companyService.getJobOpenings(company, jobOpeningList),
                                    new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value ="/jobopenings/{jobId}", method = RequestMethod.GET)
    public ResponseEntity getJobOpening(HttpServletResponse response, @PathVariable String jobId) 
    {
          JobOpening jobOpening = jobOpeningService.getJobOpeningByJobId(jobId);
          if (jobOpening == null) 
          {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HttpError(404,
            "Sorry the requested opening with id " + jobId + " does not exist").
            toString());
          }
          Company company = companyService.getCompany(jobOpening.getCompanyId());
          return new ResponseEntity<>(companyService.getJobopeningInCompany(company, jobOpening),
                                    new HttpHeaders(), HttpStatus.OK);
        
    }
        

    @RequestMapping(value ="/jobopenings/search", method = RequestMethod.GET)
    public ResponseEntity searchJobOpenings(HttpServletResponse response, 
                                            @RequestParam String q)
    {
        return new ResponseEntity<>(jobOpeningService.searchJobOpenings(q),
                                    new HttpHeaders(), HttpStatus.OK);

    }


    @RequestMapping(value ="/jobopenings", method = RequestMethod.GET)
    public ResponseEntity getJobOpeningsInCompany( HttpServletResponse response,
                                    @RequestParam Map<String, String> params) 
    {
        String companynames = "";
        String locations = ""; 
        String salaryStart = "";
        String salaryEnd = "";
    
        //check if company name is present in search
        if (params.get("companynames") != null) {
            companynames = params.get("companynames");
        } 
        
        //check if location is present in search
        if (params.get("locations") != null) {
            locations = params.get("locations");
            
        }
        
        //check if salary range is present 
        if (params.get("salary") != null) {
             salaryStart = params.get("gt");
             salaryEnd = params.get("lt");
        }
        
        List<JobOpening> jobOpenings = new ArrayList<>();
        return new ResponseEntity<>(jobOpeningService.getJobOpeningsByFilters(companynames, 
                            locations, salaryStart, salaryEnd), new HttpHeaders(),
                                    HttpStatus.OK);
        
    }

 

    
}
