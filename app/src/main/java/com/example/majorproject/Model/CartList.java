package com.example.majorproject.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class CartList implements Serializable
{
    String id;
    String userid;
    String name;
    String discount;
    String type;
    String qty;
    String price;
    int totalprice;
    String description;
    String rating;
    String img_url;

    public CartList()
    {

    }

    public CartList(JSONObject jsonObject)
    {
        try {
            this.id = jsonObject.getString("id");
            this.userid = jsonObject.getString("userid");
            this.name = jsonObject.getString("name");
            this.discount = jsonObject.getString("discount");
            this.type = jsonObject.getString("type");
            this.qty = jsonObject.getString("qty");
            this.price = jsonObject.getString("price");
            this.totalprice = Integer.parseInt(jsonObject.getString("totalprice"));
            this.description = jsonObject.getString("description");
            this.rating = jsonObject.getString("rating");
            this.img_url = jsonObject.getString("img_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getTotalprice()
    {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
