package com.example.popularmovieapp.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@Entity(tableName = "tvShows")
public class TVShows implements Serializable {

    @Expose
    @SerializedName("image_thumbnail_path") //Image url
    private String image_thumbnail_path;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("network")
    private String network;
    @Expose
    @SerializedName("country")
    private String country;
    @Expose
    @SerializedName("start_date")
    private String start_date;
    @Expose
    @SerializedName("name")
    private String name;
    @PrimaryKey
    @Expose
    @SerializedName("id")
    private int id;

    public String getImage_thumbnail_path() {
        return image_thumbnail_path;
    }

    public String getStatus() {
        return status;
    }

    public String getNetwork() {
        return network;
    }

    public String getCountry() {
        return country;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setImage_thumbnail_path(String image_thumbnail_path) {
        this.image_thumbnail_path = image_thumbnail_path;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
