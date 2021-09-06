package com.example.Practice1.resources;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.example.Practice1.classes.Orders;
import com.example.Practice1.classes.SES;
import com.example.Practice1.classes.SQS;
import com.example.Practice1.classes.sqsMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Path("/food-point")
public class HelloResource {

    //-------------------Get Request----------------------

    @GET
    @Produces("text/plain")
    public String hello() throws Exception {
        return "Sit Tight! Your meal is just few minutes away";
    }

    //-------------------Post Request-----------------------

    @POST
    @Path("/takeOrder")
    @Produces
    public Response postJSON(String payload) throws Exception {
        final Orders obj = new Gson().fromJson(payload, Orders.class);
        obj.insertpayloadintoDB(
                obj.getItemName(),
                obj.getCategory(),
                obj.getPreparationTime(),
                obj.getStatus(),
                obj.getCustomerName(),
                obj.getEmailID()
        );
        final SQS object = new Gson().fromJson(payload, SQS.class);
        object.send(object);

        System.out.println("Order has successfully pushed into SQS");
        return Response.ok(obj.Receipt()).build();
    }

    @GET
    @Path("/next")
    @Produces
    public Response getJSON() throws Exception{
        SQS obj = new SQS();
        return Response.ok(obj.read()).build();

    }
    static ObjectReader SQS_READER = new ObjectMapper().readerFor(SQS.class);
    static ObjectWriter SQS_WRITER = new ObjectMapper().writerFor(SQS.class);

    @POST
    @Path("/sendEmail")
    @Produces
    public Response postJson() throws SQLException {
        SQS object = new SQS();
        object.EmailnDel();
        return Response.ok().build();
    }


}