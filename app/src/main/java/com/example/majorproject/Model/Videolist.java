package com.example.majorproject.Model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Videolist implements Serializable
{
    String name;
    String img_url;
    String link;

    public Videolist()
    {

    }

    public Videolist(JSONObject jsonObject)
    {
        try {
            this.name = jsonObject.getString("name");
            this.img_url = jsonObject.getString("img_url");
            this.link = jsonObject.getString("link");
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
