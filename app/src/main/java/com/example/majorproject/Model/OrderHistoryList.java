package com.example.majorproject.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class OrderHistoryList
{
    String userid;
    String name;
    String date;
    int qty;
    int totalprice;
    int price;
    String img_url;
    String rating;

    public OrderHistoryList()
    {

    }

    public OrderHistoryList(JSONObject jsonObject) {
        try {
            this.userid = jsonObject.getString("userid");
            this.name = jsonObject.getString("name");
            this.date = jsonObject.getString("date");
            this.qty = Integer.parseInt(jsonObject.getString("qty"));
            this.totalprice = Integer.parseInt(jsonObject.getString("totalprice"));
            this.price = Integer.parseInt(jsonObject.getString("price"));
            this.img_url = jsonObject.getString("img_url");
            this.rating = jsonObject.getString("rating");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
