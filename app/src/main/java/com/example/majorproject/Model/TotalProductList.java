package com.example.majorproject.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TotalProductList implements Serializable
{
    String name;
    String description;
    String discount;
    String type;
    int price;
    String rating;
    String img_url;

    public TotalProductList()
    {

    }

    public TotalProductList(JSONObject jsonObject)
    {
        try {
            this.name = jsonObject.getString("name");
            this.description = jsonObject.getString("description");
            this.discount = jsonObject.getString("discount");
            this.type = jsonObject.getString("type");
            this.price = Integer.parseInt(jsonObject.getString("price"));
            this.rating = jsonObject.getString("rating");
            this.img_url = jsonObject.getString("img_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
