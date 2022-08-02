package com.example.majorproject.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Gardenlist
{
    String gardenimg_url;

    public Gardenlist()
    {

    }

    public Gardenlist(JSONObject jsonObject)
    {
        try {
            this.gardenimg_url = jsonObject.getString ("gardenimg_url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getGardenimg_url() {
        return gardenimg_url;
    }

    public void setGardenimg_url(String gardenimg_url) {
        this.gardenimg_url = gardenimg_url;
    }
}
