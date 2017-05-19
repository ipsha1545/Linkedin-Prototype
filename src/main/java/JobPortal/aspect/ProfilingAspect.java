package JobPortal.aspect; 

import java.util.concurrent.Future;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.List;
import java.util.Arrays;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Aspect
@Component
public class ProfilingAspect {


    static Properties mailServerProperties;

    static Session getMailSession;

    static MimeMessage generateMailMessage;


    @AfterReturning("execution(* JobPortal..*JobOpeningService.updateJob(..))")
    public void afterUpdateJobOpeningAdvice(JoinPoint joinPoint) throws AddressException, MessagingException
    {
        int jobId = (int) joinPoint.getArgs()[0];
        String emails = (String) joinPoint.getArgs()[1];
        String title = (String) joinPoint.getArgs()[4];
        String description = (String) joinPoint.getArgs()[5];
        String responsibilities = (String) joinPoint.getArgs()[6];
        String location = (String) joinPoint.getArgs()[7];
        int salary = (int) joinPoint.getArgs()[8];
        String status = (String) joinPoint.getArgs()[9];

        List<String> emailList = Arrays.asList(emails.split("\\s*,\\s*"));
        
        
       if ( null == emailList || emailList.size() == 0)
                return ;
        
        for (String email : emailList)
        {
            System.out.println("update aspect for " + email );
            String message = "As per our records you have applied for position : " + title + 
                             "<br>This email is to inform you that the above job opening has been updated. Details are : " +
                             "<br>Title : " + title + 
                             "<br>Description : " + description +
                             "<br>Responsibilties : " + responsibilities +
                             "<br>Location : " + location +
                             "<br>Salary : " + String.valueOf(salary) +
                             "<br>Status : " + status +
                             "<br><br> Regards,  <br> JobPortal Team <br>";
            String subject = "JobPortal : Modification in position ";
            generateAndSendEmail(email, subject, message);
        }
         
    }


   public void setup()
   {
        // Step1
        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        System.out.println("Mail Server Properties have been setup successfully..");

   }

    @Async
    public void generateAndSendEmail(String recipient, String subject, String body) 
                                throws AddressException, MessagingException 
    {

        // Step2
        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("275jobportal@gmail.com"));
        generateMailMessage.setSubject(subject);
        generateMailMessage.setContent(body, "text/html");
 
        // Step3
        Transport transport = getMailSession.getTransport("smtp");
 
        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
        transport.connect("smtp.gmail.com", "275jobportal@gmail.com","jobportal275");
        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
        transport.close();
    }
    
    ProfilingAspect()
    {
        setup();
    }

}
