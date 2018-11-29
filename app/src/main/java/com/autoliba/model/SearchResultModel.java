package com.autoliba.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ma7MouD on 11/20/2018.
 */

public class SearchResultModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private List<Message> message = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    // ------------------- Message ------------------
    public class Message {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("encoded_id")
        @Expose
        private String encodedId;
        @SerializedName("user_role")
        @Expose
        private String userRole;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("used")
        @Expose
        private String used;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("modal")
        @Expose
        private String modal;
        @SerializedName("price")
        @Expose
        private String price;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("ad_desc")
        @Expose
        private String adDesc;
        @SerializedName("neogation")
        @Expose
        private String neogation;
        @SerializedName("phonecontact")
        @Expose
        private String phonecontact;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("suspensed")
        @Expose
        private String suspensed;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("views")
        @Expose
        private String views;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getEncodedId() {
            return encodedId;
        }

        public void setEncodedId(String encodedId) {
            this.encodedId = encodedId;
        }

        public String getUserRole() {
            return userRole;
        }

        public void setUserRole(String userRole) {
            this.userRole = userRole;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUsed() {
            return used;
        }

        public void setUsed(String used) {
            this.used = used;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAdDesc() {
            return adDesc;
        }

        public void setAdDesc(String adDesc) {
            this.adDesc = adDesc;
        }

        public String getNeogation() {
            return neogation;
        }

        public void setNeogation(String neogation) {
            this.neogation = neogation;
        }

        public String getPhonecontact() {
            return phonecontact;
        }

        public void setPhonecontact(String phonecontact) {
            this.phonecontact = phonecontact;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSuspensed() {
            return suspensed;
        }

        public void setSuspensed(String suspensed) {
            this.suspensed = suspensed;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getViews() {
            return views;
        }

        public void setViews(String views) {
            this.views = views;
        }

    }
}
