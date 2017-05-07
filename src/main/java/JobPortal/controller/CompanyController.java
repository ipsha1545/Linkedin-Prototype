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

import JobPortal.model.Company;
import JobPortal.service.CompanyService;

import java.util.Map;

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

    @RequestMapping( value = "/company/{companyId}", method = RequestMethod.GET)
    public ResponseEntity getCompany(HttpServletResponse response, 
                                @PathVariable int companyId) {


        ModelAndView modelAndView = null;
        ModelMap model = new ModelMap();
        Company company = companyService.getCompany(companyId);

        model.addAttribute("companyId", company.getCompanyId());
        model.addAttribute("companyname", company.getCompanyname());
        model.addAttribute("website", company.getWebsite());
        model.addAttribute("location", company.getLocation());
        model.addAttribute("logo_image_url", company.getLogo_image_url());
        model.addAttribute("description", company.getDescription());
        return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value= "/company", method = RequestMethod.POST)
    public ResponseEntity createCompany(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params)
    {
        log.error("im here"); 
        if (params.get("companyName") == null || params.get("website") == null 
                             || params.get("description") == null)
        {
                //TODO : Raise Bad Req exception here
                log.error("parameters required");
        }
        String companyName = params.get("companyName");
        String website = params.get("website");
        String description = params.get("description");
        String location = params.get("location");
        String logoImageUrl = params.get("logoImageUrl");
        String password = params.get("password");
        String companyEmail = params.get("companyEmail");
        
        log.error("creating company"); 
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
        return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);
    }

    @RequestMapping(value= "/company/{companyId}", method = RequestMethod.PUT)
    public ResponseEntity updateCompany(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params,
                                    @PathVariable int companyId)
    {
        
        Company company = companyService.getCompany(companyId);

        if (company == null) {
            //raise an exception
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
        
        company = companyService.updateCompany(companyName, 
                        website, location,logoImageUrl, description, companyId);
        return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);

    }


   
}
