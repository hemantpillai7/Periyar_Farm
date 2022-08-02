package com.example.majorproject.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class ExploreList
{
    String name;
    String img_url;
    String type;
    public ExploreList()
    {

    }

    public ExploreList(JSONObject jsonObject)
    {
        try {
            this.name = jsonObject.getString ("name");
            this.type = jsonObject.getString("type");
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

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
