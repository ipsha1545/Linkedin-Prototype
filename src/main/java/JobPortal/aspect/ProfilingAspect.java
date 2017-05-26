package JobPortal.aspect; 

import java.util.concurrent.Future;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;

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

import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;


import java.util.Properties;
import javax.activation.DataHandler;
import javax.mail.Multipart;
import java.util.List;
import java.util.Arrays;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.BodyPart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

@Aspect
@Component
public class ProfilingAspect {


    static Properties mailServerProperties;

    static Session getMailSession;

    static MimeMessage generateMailMessage;

    //define somewhere the icalendar date format
    private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
  


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

   @AfterReturning("execution(* JobPortal..*CompanyService.scheduleInterview(..))")
   public void scheduleInterview(JoinPoint joinPoint) throws Exception
   {
        String startDate  = (String) joinPoint.getArgs()[5];
        String endDate = (String) joinPoint.getArgs()[6];
        String email = (String) joinPoint.getArgs()[3];
        String title = (String) joinPoint.getArgs()[2];
        System.out.println("im here");
        this.sendCalendarInvite(title, email, startDate, endDate);
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

    


   public void sendCalendarInvite(String position, String email, String start, String end) throws Exception {

        try {
            String from = "275jobportal@gmail.com";
            String to = email;

            getMailSession = Session.getDefaultInstance(mailServerProperties, null);


            // Define message
            MimeMessage message = new MimeMessage(getMailSession);
            message.addHeaderLine("method=REQUEST");
            message.addHeaderLine("charset=UTF-8");
            message.addHeaderLine("component=VEVENT");

            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("JobPortal : Interview invitation for the position : " +  position);

            StringBuffer sb = new StringBuffer();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            Date test = dateFormat.parse(start);  

            System.out.println("Start date is " + test.toString());
            StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n" +
                    "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" +
                    "VERSION:2.0\n" +
                    "METHOD:REQUEST\n" +
                    "BEGIN:VEVENT\n" +
                    "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:"+ from + "\n" +
                    "ORGANIZER:MAILTO:" + from + "\n" +
                    "DTSTART:" + test + "\n" +
                    "DTEND:" + test + "\n" +
                    "LOCATION:Conference room\n" +
                    "TRANSP:OPAQUE\n" +
                    "SEQUENCE:0\n" +
                    "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n" +
                    " 000004377FE5C37984842BF9440448399EB02\n" +
                    "DTSTAMP:" + test + "\n" +
                    "CATEGORIES:Meeting\n" +
                    "DESCRIPTION:Interview invitation for the position " + position + "\n\n" +
                    "SUMMARY:Interview invitation\n" +
                    "PRIORITY:5\n" +
                    "CLASS:PUBLIC\n" +
                    "BEGIN:VALARM\n" +
                    "TRIGGER:PT1440M\n" +
                    "ACTION:DISPLAY\n" +
                    "DESCRIPTION:Reminder\n" +
                    "END:VALARM\n" +
                    "END:VEVENT\n" +
                    "END:VCALENDAR");

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
            messageBodyPart.setHeader("Content-ID", "calendar_message");
            messageBodyPart.setDataHandler(new DataHandler(
            new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very important

            // Create a Multipart
            Multipart multipart = new MimeMultipart();

            // Add part one
            multipart.addBodyPart(messageBodyPart);

            // Put parts in message
            message.setContent(multipart);
            
            // send message
            Transport transport = getMailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", "275jobportal@gmail.com","jobportal275");

            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            me.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
