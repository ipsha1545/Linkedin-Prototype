package JobPortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import JobPortal.service.EmailService;

/**
 * Created by anvita on 4/18/17.
 */

@SpringBootApplication
public class Application extends SpringBootServletInitializer implements CommandLineRunner{

    @Autowired
    private EmailService emailService;
    
    @Override
    public void run(String... args) {
        System.out.println(this.emailService.getHelloMessage());
    }
    
    /*
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    */

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("context.xml");
       // ctx.close();
    
    }
}




