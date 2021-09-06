package com.example.Practice1.classes;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SQS {
    int idOrder;
    String ItemName;
    String Category;
    String EmailID;

    /*public SQS(int idOrder, String itemName, String category, String emailID) {
        this.idOrder = idOrder;
        ItemName = itemName;
        Category = category;
        EmailID = emailID;
    }*/

    public int getIdOrder() {
        return idOrder;
    }

    public String getItemName() {
        return ItemName;
    }
    public String getCategory() {
        return Category;
    }
    public String getEmailID() {
        return EmailID;
    }

    static ObjectWriter SQS_WRITER = new ObjectMapper().writerFor(SQS.class);


    public Object send(SQS sqsobj) {
        AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();
        String queueURL = "http://localhost:4566/000000000000/OrdersQueue";
        String message = null;
        try {
            message = SQS_WRITER.writeValueAsString(sqsobj);
            System.out.println(message);
            SendMessageRequest send_msg_req = new SendMessageRequest().withQueueUrl(queueURL)
                    .withMessageBody(message)
                    .withDelaySeconds(0);
            SendMessageResult send_msg_rslt = sqs.sendMessage(send_msg_req);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return queueURL;
    }

    public String read() {
        try {
            AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();
            String queueURL = "http://localhost:4566/000000000000/OrdersQueue";
            ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
                    .withQueueUrl(queueURL)
                    .withVisibilityTimeout(0)
                    .withMaxNumberOfMessages(1);
            List<Message> messages = sqs.receiveMessage(receive_request).getMessages();
            for (Message msg : messages) {
                System.out.println("Message received");
                System.out.println(msg.getBody());
                System.out.println("______________________________________________");
                return "{" +
                        "Message Content: " + msg.getBody() + '\n' +
                        "Message ID: " + msg.getMessageId() + '\n' +
                        "Receipt Handle: " + msg.getReceiptHandle() + '\n' +
                        '}';
            }
        } catch (Exception exp) {
            System.out.println("Failed!!");
        }
        return null;
    }
    public void delete() {
        try {
            AmazonSQS sqs = AmazonSQSClientBuilder.standard().build();
            String queueURL = "http://localhost:4566/000000000000/OrdersQueue";
            ReceiveMessageRequest receive_request = new ReceiveMessageRequest()
                    .withQueueUrl(queueURL)
                    .withVisibilityTimeout(10)
                    .withMaxNumberOfMessages(1);
            List<Message> messages = sqs.receiveMessage(receive_request).getMessages();
            for (Message msg : messages) {
                DeleteMessageResult res = sqs.deleteMessage(queueURL, msg.getReceiptHandle());
                System.out.println(res);
                System.out.println("Message has been deleted successfully");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void EmailnDel() throws SQLException {
        try {
            String message = read();
            sqsMessage object = new Gson().fromJson(message, sqsMessage.class);
            SES obj = new SES();
            obj.notifyUserByEmail(object.getEmail());
        }
        catch (Exception e) {
            System.out.println("Email failed to send.. Error: " + e.getMessage());
        }
        delete();
        updateDataBase();

    }

    public void updateDataBase() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        int rs4 = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fooddelivery", "root", "");
            stmt = conn.createStatement();
            rs4 = stmt.executeUpdate("DELETE FROM fooddelivery.orders LIMIT 1");
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            conn.close();
            stmt.close();
        }
    }
}
