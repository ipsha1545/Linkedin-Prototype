package JobPortal.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    
    private String address;

    private String body;

    @Value("${name:World}")
    private String name;
    public String getHelloMessage() {
        return "Hello " + this.name;
    }


    public String sendEmail()
    {
        System.out.println("sending email");
        return "hey";
    }
}
