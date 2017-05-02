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
        model.addAttribute("logo_image_URL", company.getLogo_image_URL());
        model.addAttribute("description", company.getDescription());
        return new ResponseEntity<>(model, new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping(value= "/company", method = RequestMethod.POST)
    public ResponseEntity createCompany(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params)
    {
        
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
        
        log.error("creating company"); 
        Company company = companyService.createCompany(companyName, 
                        website, location,logoImageUrl, description);
         
        ModelMap model = new ModelMap();
        model.addAttribute("companyId", company.getCompanyId());
        model.addAttribute("companyname", company.getCompanyname());
        model.addAttribute("website", company.getWebsite());
        model.addAttribute("location", company.getLocation());
        model.addAttribute("logo_image_URL", company.getLogo_image_URL());
        model.addAttribute("description", company.getDescription());
        return new ResponseEntity<>(company, new HttpHeaders(), HttpStatus.OK);


    }
}
