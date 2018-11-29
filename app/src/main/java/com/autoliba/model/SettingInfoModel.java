package com.autoliba.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ma7MouD on 11/12/2018.
 */

public class SettingInfoModel {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("message")
    @Expose
    private Message message;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    // --------------- Message ----------------
    public class Message {

        @SerializedName("setting")
        @Expose
        private List<Setting> setting = null;
        @SerializedName("modals")
        @Expose
        private List<Modal> modals = null;
        @SerializedName("counts")
        @Expose
        private List<Count> counts = null;
        @SerializedName("types")
        @Expose
        private List<Type> types = null;

        public List<Setting> getSetting() {
            return setting;
        }

        public void setSetting(List<Setting> setting) {
            this.setting = setting;
        }

        public List<Modal> getModals() {
            return modals;
        }

        public void setModals(List<Modal> modals) {
            this.modals = modals;
        }

        public List<Count> getCounts() {
            return counts;
        }

        public void setCounts(List<Count> counts) {
            this.counts = counts;
        }

        public List<Type> getTypes() {
            return types;
        }

        public void setTypes(List<Type> types) {
            this.types = types;
        }

    }

    // ------------------ Modal -----------------
    public class Modal {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("modal")
        @Expose
        private String modal;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getModal() {
            return modal;
        }

        public void setModal(String modal) {
            this.modal = modal;
        }

    }

    // ---------------- Type ---------------------
    public class Type {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("city")
        @Expose
        private List<City_> city = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<City_> getCity() {
            return city;
        }

        public void setCity(List<City_> city) {
            this.city = city;
        }

    }

    // --------------- Settings ------------------
    public class Setting {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("header_logo")
        @Expose
        private String headerLogo;
        @SerializedName("footer_logo")
        @Expose
        private String footerLogo;
        @SerializedName("main_image")
        @Expose
        private String mainImage;
        @SerializedName("aboutus")
        @Expose
        private String aboutus;
        @SerializedName("policy")
        @Expose
        private String policy;
        @SerializedName("advertise")
        @Expose
        private String advertise;
        @SerializedName("privacy")
        @Expose
        private String privacy;
        @SerializedName("facebook")
        @Expose
        private String facebook;
        @SerializedName("youtube")
        @Expose
        private String youtube;
        @SerializedName("twitter")
        @Expose
        private String twitter;
        @SerializedName("rss")
        @Expose
        private String rss;
        @SerializedName("dribble")
        @Expose
        private String dribble;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getHeaderLogo() {
            return headerLogo;
        }

        public void setHeaderLogo(String headerLogo) {
            this.headerLogo = headerLogo;
        }

        public String getFooterLogo() {
            return footerLogo;
        }

        public void setFooterLogo(String footerLogo) {
            this.footerLogo = footerLogo;
        }

        public String getMainImage() {
            return mainImage;
        }

        public void setMainImage(String mainImage) {
            this.mainImage = mainImage;
        }

        public String getAboutus() {
            return aboutus;
        }

        public void setAboutus(String aboutus) {
            this.aboutus = aboutus;
        }

        public String getPolicy() {
            return policy;
        }

        public void setPolicy(String policy) {
            this.policy = policy;
        }

        public String getAdvertise() {
            return advertise;
        }

        public void setAdvertise(String advertise) {
            this.advertise = advertise;
        }

        public String getPrivacy() {
            return privacy;
        }

        public void setPrivacy(String privacy) {
            this.privacy = privacy;
        }

        public String getFacebook() {
            return facebook;
        }

        public void setFacebook(String facebook) {
            this.facebook = facebook;
        }

        public String getYoutube() {
            return youtube;
        }

        public void setYoutube(String youtube) {
            this.youtube = youtube;
        }

        public String getTwitter() {
            return twitter;
        }

        public void setTwitter(String twitter) {
            this.twitter = twitter;
        }

        public String getRss() {
            return rss;
        }

        public void setRss(String rss) {
            this.rss = rss;
        }

        public String getDribble() {
            return dribble;
        }

        public void setDribble(String dribble) {
            this.dribble = dribble;
        }

    }

    // --------------------------------
    public class City_ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    // -------------------------------------
    public class Count {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("city")
        @Expose
        private List<City> city = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<City> getCity() {
            return city;
        }

        public void setCity(List<City> city) {
            this.city = city;
        }

    }
    // -------------------------------

    public class Example {

        @SerializedName("success")
        @Expose
        private Boolean success;
        @SerializedName("data")
        @Expose
        private String data;
        @SerializedName("message")
        @Expose
        private Message message;

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

        public Message getMessage() {
            return message;
        }

        public void setMessage(Message message) {
            this.message = message;
        }

    }

    // -------------- City ---------------------
    public class City {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

}
