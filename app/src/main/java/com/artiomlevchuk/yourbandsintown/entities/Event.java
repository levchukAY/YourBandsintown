package com.artiomlevchuk.yourbandsintown.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Event {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("venue")
    @Expose
    private Venue venue;
    @SerializedName("offers")
    @Expose
    private List<Offer> offers = null;

    public Event(String url, String datetime, String name, String latitude, String longitude,
                 String city, String country, String offersUrl, String status) {
        this.url = url;
        this.datetime = datetime;
        this.venue = new Venue(name, latitude, longitude, city, country);
        this.offers = new ArrayList<>();
        if (offersUrl != null)
            this.offers.add(new Offer(offersUrl, status));
    }

    public String getUrl() {
        return url;
    }

    public String getDatetime() {
        return datetime;
    }

    public Venue getVenue() {
        return venue;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public String getDate() {
        return datetime.substring(8, 10);
    }

    public String getMonth() {
        String month = "";
        switch (datetime.substring(5, 7)) {
            case "01":
                month = "JAN";
                break;
            case "02":
                month = "FEB";
                break;
            case "03":
                month = "MAR";
                break;
            case "04":
                month = "APR";
                break;
            case "05":
                month = "MAY";
                break;
            case "06":
                month = "JUN";
                break;
            case "07":
                month = "JUL";
                break;
            case "08":
                month = "AUG";
                break;
            case "09":
                month = "SEP";
                break;
            case "10":
                month = "OCT";
                break;
            case "11":
                month = "NOV";
                break;
            case "12":
                month = "DEC";
                break;
        }
        return month;
    }

    public class Offer {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("status")
        @Expose
        private String status;

        Offer(String url, String status) {
            this.url = url;
            this.status = status;
        }

        public String getUrl() {
            return url;
        }

        public String getStatus() {
            return status;
        }

    }

    public class Venue {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("latitude")
        @Expose
        private String latitude;
        @SerializedName("longitude")
        @Expose
        private String longitude;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("country")
        @Expose
        private String country;

        Venue(String name, String latitude, String longitude, String city, String country) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
            this.city = city;
            this.country = country;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getCity() {
            return city;
        }

        public String getCountry() {
            return country;
        }

        public String getLocation() {
            StringBuilder location = new StringBuilder();
            if (!city.isEmpty())
                location.append(city + ", ");
            return location + country;
        }

    }

}