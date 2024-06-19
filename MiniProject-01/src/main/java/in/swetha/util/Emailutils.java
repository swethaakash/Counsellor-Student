package in.swetha.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

/*Email sending in springBoot

1. Mail -starter dependency in pom.xml
2. Generate APP pwd for your gmail account
3. Configure SMTP props in applocation.properties file
4. Inject javaMailSender to send emails
5. we can use below two classes to create mail
 a. SimpleMessage(Plain text data)
 b. MimeMessage (html tags+ Attachments)
 
 Gmail APP Password: //ncey njjm zeua qcvy//
 Gmail: vejendlaswetha02@gmail.com
 *
 *
 */


@Component
public class Emailutils {
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String subject, String body, String to) {
		boolean isSent = false;
		try {
			
			 MimeMessage mimeMsg = mailSender.createMimeMessage();	// creating mime message from JavaMailSender
			 
			 MimeMessageHelper helper = new MimeMessageHelper(mimeMsg); // Creating MimeMessageHelper and Taking createdmimemessage as Input
			 helper.setTo(to); // set to address
			 helper.setSubject(subject);// Set Subject
			 helper.setText(body, true);//Set body
			 
			 mailSender.send(mimeMsg); // send mimemsg object with all three parameters
			 isSent = true;
			 
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return isSent;
		
	}
	

}
