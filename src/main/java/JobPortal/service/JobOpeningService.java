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

        JobOpening jobOpening = null; 
        try {       
            jobOpening = new JobOpening(Integer.valueOf(company.getCompanyId()), company.getCompanyname(),  
                                     title, description, responsibilities, location, 
                                     Integer.valueOf(salary));
            jobOpening = jobOpeningDao.save(jobOpening);
            return jobOpening;
        } catch (Exception e)
        {
            return null;
        }
    } 
    
}
