package JobPortal.service;

import JobPortal.Dao.JobOpeningDao;
import JobPortal.model.JobOpening;

import JobPortal.model.Company;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by ipshamohanty on 5/1/17.
 */

@Service
@Transactional
public class JobOpeningService {

    @Autowired
    private JobOpeningDao jobOpeningDao;

    public JobOpeningService() {

    }

    public JobOpeningService(JobOpeningDao jobopeningDao) {
        this.jobOpeningDao = jobOpeningDao;
    }

    public JobOpening getJobOpening(int jobId) {

        JobOpening jobOpening = null;
        return jobOpeningDao.findByJobId(jobId);
    }
    

    public JobOpening createJobOpening(Company company, String title, String description,
                String responsibilities, String location, String salary)
    {

        JobOpening jobOpening; 
        try {       
            jobOpening = new JobOpening(Integer.valueOf(company.getCompanyId()), company.getCompanyname(),  
                                     title, description, responsibilities, location, 
                                     Integer.valueOf(salary));
            JobOpening newJobOpening = jobOpeningDao.save(jobOpening);
            return newJobOpening;
        } catch (Exception e)
        {
            return null;
        }
    } 
   
    //@Transactional(readOnly = true)
    public List<JobOpening> getJobOpeningsInCompany(String companyId)
    {
       String query = "select * from job_openings where job_openings.companyId = " + companyId;
       List<JobOpening> jobOpeningList = new ArrayList<>();
       jobOpeningList = jobOpeningDao.findJobOpeningsInCompany(companyId);
       return jobOpeningList;
    } 
}
