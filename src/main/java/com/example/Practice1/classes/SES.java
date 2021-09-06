package com.example.Practice1.classes;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;


public class SES {

    public void notifyUserByEmail(String Email) {
        try {
            String Sender = "11amnasaleem11@gmail.com";
            String EmailSubject = "Order Status";
            final String EmailContent = "Dear Customer, You Order is ready to deliver" +
                                    "We look forward to serve you again";
            AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder
                    .standard().withEndpointConfiguration
                            (new AwsClientBuilder.EndpointConfiguration
                                    ("http://localhost:4566", "us-east-1"))
                    .build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(new Destination().withToAddresses())
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(EmailContent)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(EmailSubject)))
                    .withSource(Sender);
            //client.sendEmail(request);
            System.out.println("Email generated successfully");
            System.out.println(EmailContent);
        } catch (Exception ex) {
            System.out.println("Failed to send email.. Error message: " + ex.getMessage());
        }
    }


}
