package com.appsdevelopersblog.app.ws.shared;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import com.appsdevelopersblog.app.ws.shared.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public class AmazonSES {

    //This is the email we verified for Amazon SES.
    final String FROM = "necmettingedikli611@gmail.com";

    // The subject line for the email.
    final String SUBJECT = "One last step to complete your registration with PhotoApp";

    final String PASSWORD_RESET_SUBJECT = "Password reset request";

    // The HTML body for the email.
    final String HTMLBODY = "<h1>Please verify your email address</h1>"
            + "<p>Thank you for registering with our mobile app. To complete registration process and be able to log in,"
            + " click on the following link: "
            + "<a href='http://ec2-54-93-53-99.eu-central-1.compute.amazonaws.com:8080/verification-service/email-verification.html?token=$tokenValue'>"
            + "Final step to complete your registration" + "</a><br/><br/>"
            + "Thank you! And we are waiting for you inside!";

    // The email body for recipients with non-HTML email clients.
    final String TEXTBODY = "Please verify your email address. "
            + "Thank you for registering with our mobile app. To complete registration process and be able to log in,"
            + " open then the following URL in your browser window: "
            + " http://ec2-54-93-53-99.eu-central-1.compute.amazonaws.com:8080/verification-service/email-verification.html?token=$tokenValue"
            + " Thank you! And we are waiting for you inside!";



    public void verifyEmail(UserDto userDto) {

        // You can also set your keys this way. And it will work!
        //System.setProperty("aws.accessKeyId", "<YOUR KEY ID HERE>");
        //System.setProperty("aws.secretKey", "<SECRET KEY HERE>");

        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(Regions.EU_CENTRAL_1)
                .build();

        //We are going to replace $tokenValue placeholder inside of Html body with token value from UserDto instance.
        String htmlBodyWithToken = HTMLBODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        String textBodyWithToken = TEXTBODY.replace("$tokenValue", userDto.getEmailVerificationToken());

        //We are creating simple email request object.
        SendEmailRequest request = new SendEmailRequest()
                //Email address of the user that has just registered is the destination.
                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                //We set message with two different versions. One is html and one is text when user clients doesn't support html.
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                        //We set subject with the SUBJECT string above.
                        .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                //From is the email we verified for Aws SES (sender).
                .withSource(FROM);

        //Send the email.
        client.sendEmail(request);

        System.out.println("Email sent!");

    }
}
