package JobPortal.controller;


import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.io.IOException;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class JobOpeningsController {
    
    private final Logger log = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value ="/jobopening", method = RequestMethod.POST)
    public void createJobOpening(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params) 
    {
        log.error("Creating an opening");
    }

    /*
    @RequestMapping(value ="/jobopening", method = RequestMethod.POST)
    public void updateJobOpening(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params) 
    {
        log.error("Creating an opening");
    }


    @RequestMapping(value ="/jobopening", method = RequestMethod.GET)
    public void getJobOpening(HttpServletResponse response, 
                                    @RequestParam Map<String,String> params) 
    {
        log.error("Creating an opening");
    }
    */

}
