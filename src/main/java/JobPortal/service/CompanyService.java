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

    public Company createCompany(String companyname, String website, String location, 
                     String logoImageUrl, String description, String password) {


        ModelAndView modelAndView;
        ObjectMapper mapper = new ObjectMapper();
        ModelMap model = new ModelMap();

        Company company;

        try {
            company = new Company(companyname, website, location, logoImageUrl, description);
            Company newCompany = companyDao.save(company);
            return newCompany;
        } catch(Exception ex) {
           //TODO : Handle exception  
            return null;
        }
    }

   public Company updateCompany(String companyName, String website, String location, 
                                    String logoImageUrl, String description, int companyId)
  {
        Company company = companyDao.findBycompanyId(companyId);
        if (company == null) {
            //todo : company not found 
            //raise error
        } 
        
       try {
            company = new Company(companyName, website, location, logoImageUrl, description);
            company.setCompanyId(companyId);
            company = companyDao.save(company);
            return company;
        } catch(Exception ex) {
           //TODO : Handle exception  
            return null;
        }
  }





}
