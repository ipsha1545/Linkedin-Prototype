package JobPortal.service;

import JobPortal.Dao.CompanyDao;

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
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;

    public CompanyService(){

    }

    public CompanyService(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }

    public Company getCompany(int companyId){

        Company company = null;

        return companyDao.findBycompanyId(companyId);
    }
    public void createCompany(String companyname, String website, String location, String logo_image_URL, String description){


        ModelAndView modelAndView;
        ObjectMapper mapper = new ObjectMapper();
        ModelMap model = new ModelMap();

        Company company;

        try{

            System.out.println("received company details in company service");

            company = new Company(companyname, website, location, logo_image_URL, description);
            System.out.println("reached 1");

            companyDao.save(company);
            System.out.println("reached");


        }catch(Exception ex){
            String message = "Another company with the same number already exists.";

        }

    }





}
