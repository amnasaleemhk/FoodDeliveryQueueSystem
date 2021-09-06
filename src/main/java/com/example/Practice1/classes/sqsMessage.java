package com.example.Practice1.classes;

import com.google.gson.Gson;

public class sqsMessage extends SQS {
    int orderid;
    String itemname;
    String category;
    String email;

    public sqsMessage(int orderid, String itemname, String category, String email) {
        this.orderid = orderid;
        this.itemname = itemname;
        this.category = category;
        this.email = email;
    }

    public int getOrderid() {
        return orderid;
    }

    public String getItemname() {
        return itemname;
    }

    public String getCategory() {
        return category;
    }

    public String getEmail() {
        return email;
    }

    public String SQSMessageBody(){

        return "{" +
                "Order ID " + orderid + '\n' +
                "Item Name: '" + itemname + '\n' +
                "Category: '" + category + '\n' +
                "Customer Email: " + email  +  '\n' +
                '}';

    }

}
