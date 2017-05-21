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

    public ResponseEntity apply_Job(int userid, int jobid, String resume) {

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
                    
                    if(resume != null){
                        jobOpening_user.setResume(resume);
                    }
                    
                    jobOpening_userDao.save(jobOpening_user);

                    return new ResponseEntity(jobOpening_user, HttpStatus.OK);

                } else {

                    jobOpening_user.setStatus("applied");
                    
                     if(resume != null){
                        jobOpening_user.setResume(resume);
                    }
                    
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
                List<ModelMap> applications = new ArrayList<>();

                ModelMap all_applications = new ModelMap();

                for(int i = 0; i < job_applications.size(); i++){
                    ModelMap m = new ModelMap();

                    m.addAttribute("applicationID", job_applications.get(i).getJob_userId());
                    m.addAttribute("jobid", job_applications.get(i).getJobId());
                    m.addAttribute("companyid", job_applications.get(i).getCompanyId());
                    m.addAttribute("userid", job_applications.get(i).getUserId());
                    m.addAttribute("interested", job_applications.get(i).isInterested());
                    m.addAttribute("status", job_applications.get(i).getStatus());
                    m.addAttribute("terminal", job_applications.get(i).isTerminal());
                    m.addAttribute("resume", job_applications.get(i).getResume());
                    m.addAttribute("user", userDao.findByuserId(job_applications.get(i).getUserId()));

                    applications.add(m);

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

    public String getActiveCompanyApplications(int jobid) {
        String res = "";
        try {

            JobOpening jobOpening = jobOpeningDao.findJobOpeningByJobId(jobid);

            if (jobOpening != null) {
                List<JobOpening_User> job_applications = jobOpening_userDao.getActiveCompanyApplications(jobid);
                Object[] applications = new Object[job_applications.size()];

                //ModelMap m = new ModelMap();
                //ModelMap all_applications = new ModelMap();

                for(int i = 0; i < job_applications.size(); i++) {
                    User user = userDao.findByuserId(job_applications.get(i).getUserId());
                    res += user.getEmail() + ",";
                }
                return res;
            }
        } catch (Exception e) {

            ModelMap m = new ModelMap();
            throw new RuntimeException();
        }

        return res;
    }

    public ResponseEntity changeStatus(int applicationId, String status) {

        try {

            JobOpening_User jobOpening_user = jobOpening_userDao.findByJob_userId(applicationId);

            if(status.contains("Company")){

                if(status.contains("Cancel")) {
                    if(jobOpening_user.getStatus().equals("Applied") || jobOpening_user.getStatus().equals("Offered") ||
                            jobOpening_user.getStatus().contains("Schedule")){

                        int jobId = jobOpening_user.getJobId();
                        List<JobOpening_User> offerJobs = jobOpening_userDao.getOfferJobs(jobId);

                        if(offerJobs.size() == 0){
                            if(jobOpening_user.getStatus().equals("Applied")){
                                User u = userDao.findByuserId(jobOpening_user.getUserId());
                                int p = u.getPending_applications();
                                u.setPending_applications(p-1);
                                userDao.save(u);
                            }

                            jobOpening_user.setStatus(status);
                            jobOpening_user.setTerminal(true);
                            jobOpening_userDao.save(jobOpening_user);

                            //Update Non terminal applications
                            List<JobOpening_User> nonTerminal = jobOpening_userDao.getNonTerminalApplications(jobId);
                            for(int i = 0; i < nonTerminal.size(); i++){
                                nonTerminal.get(i).setInterested(false);
                                nonTerminal.get(i).setStatus(status);
                                jobOpening_userDao.save(nonTerminal.get(i));
                            }

                            User user = userDao.findByuserId(jobOpening_user.getUserId());
                            ModelMap m = new ModelMap();
                            m.addAttribute("status", "Cancelled by company");
                            m.addAttribute("user", user);

                            return new ResponseEntity(m, HttpStatus.OK);
                        }else{
                            ModelMap m = new ModelMap();
                            m.addAttribute("code", 400);
                            m.addAttribute("msg", "Job has one or more applications in offer accepted state");
                            return new ResponseEntity(m, HttpStatus.BAD_REQUEST);
                        }
                    }else{
                        System.out.println("cannot cancel");
                        return new ResponseEntity("Cannot cancel", HttpStatus.BAD_REQUEST);
                    }

                }

                if(status.contains("Schedule")) {
                    if (jobOpening_user.getStatus().equals("Applied")) {

                        if(jobOpening_user.getStatus().equals("Applied")){
                            User u = userDao.findByuserId(jobOpening_user.getUserId());
                            int p = u.getPending_applications();
                            u.setPending_applications(p-1);
                            userDao.save(u);
                        }

                        jobOpening_user.setStatus("Schedule Interview");
                        jobOpening_userDao.save(jobOpening_user);

                        User user = userDao.findByuserId(jobOpening_user.getUserId());
                        ModelMap m = new ModelMap();
                        m.addAttribute("status", "Schedule Interview");
                        m.addAttribute("user", user);

                        return new ResponseEntity(m, HttpStatus.OK);

                    } else {
                        System.out.println("cannot schedule");
                        return new ResponseEntity("Cannot schedule", HttpStatus.BAD_REQUEST);
                    }
                }

                if(status.contains("Reject")) {

                    if(jobOpening_user.getStatus().equals("Applied") || jobOpening_user.getStatus().equals("Interview Accepted")){

                        if(jobOpening_user.getStatus().equals("Applied")){
                            User u = userDao.findByuserId(jobOpening_user.getUserId());
                            int p = u.getPending_applications();
                            u.setPending_applications(p-1);
                            userDao.save(u);
                        }

                        jobOpening_user.setStatus("Rejected");
                        jobOpening_user.setTerminal(true);
                        jobOpening_userDao.save(jobOpening_user);

                        User user = userDao.findByuserId(jobOpening_user.getUserId());
                        ModelMap m = new ModelMap();
                        m.addAttribute("status", "Rejected");
                        m.addAttribute("user", user);

                        return new ResponseEntity(m, HttpStatus.OK);
                    }else{

                        return new ResponseEntity("Cannot reject", HttpStatus.BAD_REQUEST);
                    }
                }


                if(status.contains("Offered")) {

                    if(jobOpening_user.getStatus().equals("Interview Accepted")){
                        jobOpening_user.setStatus("Offered");
                        jobOpening_userDao.save(jobOpening_user);

                        User user = userDao.findByuserId(jobOpening_user.getUserId());
                        ModelMap m = new ModelMap();
                        m.addAttribute("status", "Offered");
                        m.addAttribute("user", user);

                        return new ResponseEntity(m, HttpStatus.OK);
                    }else{

                        return new ResponseEntity("Cannot reject", HttpStatus.BAD_REQUEST);
                    }
                }


            }else if(status.contains("User")){

                if(status.contains("Interview")) {

                    if(status.contains("Accept")) {
                        jobOpening_user.setStatus("Interview Accepted");
                        jobOpening_userDao.save(jobOpening_user);

                    }else if(status.contains("Refuse")){
                        jobOpening_user.setStatus("Interview Refused");
                        jobOpening_userDao.save(jobOpening_user);
                    }
                    return new ResponseEntity(HttpStatus.OK);
                }

                if(status.contains("Cancel")){

                    if(jobOpening_user.getStatus().equals("Applied")){
                        User u = userDao.findByuserId(jobOpening_user.getUserId());
                        int p = u.getPending_applications();
                        u.setPending_applications(p-1);
                        userDao.save(u);
                    }

                    jobOpening_user.setStatus("Cancelled by user");
                    jobOpening_userDao.save(jobOpening_user);
                    return new ResponseEntity(HttpStatus.OK);
                }

                if(status.contains("Offer")){
                    if(status.contains("Accepted")){
                        jobOpening_user.setStatus("OfferAccepted");
                        jobOpening_user.setTerminal(true);
                        jobOpening_userDao.save(jobOpening_user);
                        return new ResponseEntity(HttpStatus.OK);

                    }else if(status.contains("Rejected")){
                        jobOpening_user.setStatus("OfferRejected");
                        jobOpening_user.setTerminal(true);
                        jobOpening_userDao.save(jobOpening_user);
                        return new ResponseEntity(HttpStatus.OK);
                    }
                }
            }
        } catch (Exception e) {

                return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}
