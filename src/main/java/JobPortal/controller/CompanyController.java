package JobPortal.controller;

import JobPortal.model.Company;
import JobPortal.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ipshamohanty on 5/1/17.
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(value= "/company/{companyId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> getById(HttpServletResponse response, @PathVariable(value="companyId") int companyId) {


        Company company;

        ModelAndView modelAndView = null;
        ModelMap model = new ModelMap();


        company = companyService.getCompany(companyId);

        model.addAttribute("companyId", company.getCompanyId());
        model.addAttribute("companyname", company.getCompanyname());
        model.addAttribute("website", company.getWebsite());
        model.addAttribute("location", company.getLocation());
        model.addAttribute("logo_image_URL", company.getLogo_image_URL());
        model.addAttribute("description", company.getDescription());

        ////companyId,companyname,website,location,logo_image_URL,description

        return ResponseEntity.ok(model);


    }


    @RequestMapping(value= "/company", method = RequestMethod.POST)
    public void create(HttpServletResponse response,
                       @RequestParam(value="companyname", required = true) String companyname,
                       @RequestParam(value="website", required = true) String website,
                       @RequestParam(value="location", required = false) String location,
                       @RequestParam(value="logo_image_URL", required = false) String logo_image_URL,
                       @RequestParam(value="description", required = true) String description
    ) throws IOException {
        System.out.println(companyname);
        System.out.println(website);
        System.out.println(location);
        System.out.println(logo_image_URL);
        System.out.println(description);

        companyService.createCompany(companyname, website, location, logo_image_URL, description);

    }




}
