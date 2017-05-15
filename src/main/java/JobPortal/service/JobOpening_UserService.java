package JobPortal.service;

import JobPortal.Dao.CompanyDao;
import JobPortal.Dao.JobOpeningDao;
import JobPortal.Dao.JobOpening_UserDao;
import JobPortal.Dao.UserDao;
import JobPortal.model.JobOpening;
import JobPortal.model.JobOpening_User;
import JobPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anvita on 5/13/17.
 */

@Service
@Transactional
public class JobOpening_UserService {


    @Autowired
    private JobOpeningDao jobOpeningDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private JobOpening_UserDao jobOpening_userDao;

    public boolean markNotInterested(int userId, int jobId) {

        try {
            JobOpening_User jobOpening_user = jobOpening_userDao.checkEntry(userId, jobId);

            if(jobOpening_user != null){

                jobOpening_user.setInterested(false);
                jobOpening_userDao.save(jobOpening_user);
                return true;
            }
            return false;
        } catch (Exception e)
        {
            return false;
        }
    }

    public boolean markInterested(int userId, int jobId) {

        try {
            JobOpening_User jobOpening_user = jobOpening_userDao.checkEntry(userId, jobId);

            if(jobOpening_user == null){

                int companyId = jobOpeningDao.findByJobId(jobId).getCompanyId();
                jobOpening_user = new JobOpening_User(userId, jobId, companyId, null, true, false);
                jobOpening_userDao.save(jobOpening_user);
                return true;
            }else{

                jobOpening_user.setInterested(true);
                jobOpening_userDao.save(jobOpening_user);
                return true;
            }
        } catch (Exception e)
        {
            return false;
        }
    }

    public List<Integer> getUserInterestJobs(int userId) {

        try {
            List<Integer> userInterestJobs = jobOpening_userDao.getUserInterestJobs(userId);

            return userInterestJobs;

        } catch (Exception e)
        {
            return null;
        }
    }

    public HashMap getUserJobStatus(int userid){
        try{

            List<JobOpening_User> jobStatus = jobOpening_userDao.getUserJobStatus(userid);

            HashMap<String, String> hm = new HashMap<>();

            for(int i = 0; i < jobStatus.size(); i++){
                hm.put(String.valueOf(jobStatus.get(i).getJobId()), jobStatus.get(i).getStatus());
            }

            return hm;

        }catch(Exception e){
            return null;
        }
    }

    public ResponseEntity apply_Job(int userid, int jobid) {

        try {

            User u = userDao.findByuserId(userid);
            int pending = u.getPending_applications();
            if (pending < 5) {
                pending = pending + 1;
                u.setPending_applications(pending);
                userDao.save(u);

                JobOpening_User jobOpening_user = jobOpening_userDao.checkEntry(userid, jobid);

                if (jobOpening_user == null) {

                    int companyId = jobOpeningDao.findByJobId(jobid).getCompanyId();
                    jobOpening_user = new JobOpening_User(userid, jobid, companyId, "applied", false, false);
                    jobOpening_userDao.save(jobOpening_user);

                    return new ResponseEntity(jobOpening_user, HttpStatus.OK);

                } else {

                    jobOpening_user.setStatus("applied");
                    jobOpening_userDao.save(jobOpening_user);

                    return new ResponseEntity(jobOpening_user, HttpStatus.OK);
                }
            } else {
                ModelMap m = new ModelMap();
                m.addAttribute("msg", "too many pending applications");
                return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {

            ModelMap m = new ModelMap();
            m.addAttribute("msg", "Method failure");
            return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity getUserAllInterestedJobs(int userid) {

        User user = userDao.findByuserId(userid);

        if(user != null){

            try{

                List<Integer> userInterests = jobOpening_userDao.getUserInterestJobs(userid);

                Object[] interestedJobs = new Object[userInterests.size()];

                for(int i = 0; i < userInterests.size(); i++){
                    interestedJobs[i] = jobOpeningDao.findByJobId(userInterests.get(i));
                }

                ModelMap m = new ModelMap();
                m.addAttribute("jobs", interestedJobs);

                return new ResponseEntity(m, HttpStatus.OK);

            }catch(Exception e){
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }


        }else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity getCompanyApplications(int jobid) {
        try {

            JobOpening jobOpening = jobOpeningDao.findJobOpeningByJobId(jobid);

            if(jobOpening != null){
                List<JobOpening_User> job_applications = jobOpening_userDao.getCompanyApplication(jobid);
                Object[] applications = new Object[job_applications.size()];

                ModelMap m = new ModelMap();
                ModelMap all_applications = new ModelMap();

                for(int i = 0; i < job_applications.size(); i++){
                    m.addAttribute("applicationID", job_applications.get(i).getJob_userId());
                    m.addAttribute("jobid", job_applications.get(i).getJobId());
                    m.addAttribute("companyid", job_applications.get(i).getCompanyId());
                    m.addAttribute("userid", job_applications.get(i).getUserId());
                    m.addAttribute("interested", job_applications.get(i).isInterested());
                    m.addAttribute("status", job_applications.get(i).getStatus());
                    m.addAttribute("terminal", job_applications.get(i).isTerminal());
                    m.addAttribute("resume", job_applications.get(i).getResume());
                    m.addAttribute("user", userDao.findByuserId(job_applications.get(i).getUserId()));

                    applications[i] = m;
                }

                all_applications.addAttribute("company_application", applications);

                return new ResponseEntity(all_applications, HttpStatus.OK);

            }else{
                ModelMap m = new ModelMap();
                m.addAttribute("msg", "Company does not exists");
                return new ResponseEntity(m, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {

            ModelMap m = new ModelMap();
            m.addAttribute("msg", "Method failure");
            return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
        }
    }

}
