package com.autoliba.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ma7MouD on 11/14/2018.
 */

public class ProfileDataModel {

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

    // ---------- Message -----------------------

    public class Message {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("encoded_id")
        @Expose
        private String encodedId;
        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("phone")
        @Expose
        private String phone;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("bio")
        @Expose
        private String bio;
        @SerializedName("firebase_token")
        @Expose
        private Object firebaseToken;
        @SerializedName("device_id")
        @Expose
        private Object deviceId;
        @SerializedName("forgetcode")
        @Expose
        private String forgetcode;
        @SerializedName("suspensed")
        @Expose
        private String suspensed;
        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("countryname")
        @Expose
        private Object countryname;
        @SerializedName("cityname")
        @Expose
        private Object cityname;
        @SerializedName("myads")
        @Expose
        private List<Myad> myads = null;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getEncodedId() {
            return encodedId;
        }

        public void setEncodedId(String encodedId) {
            this.encodedId = encodedId;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public Object getFirebaseToken() {
            return firebaseToken;
        }

        public void setFirebaseToken(Object firebaseToken) {
            this.firebaseToken = firebaseToken;
        }

        public Object getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(Object deviceId) {
            this.deviceId = deviceId;
        }

        public String getForgetcode() {
            return forgetcode;
        }

        public void setForgetcode(String forgetcode) {
            this.forgetcode = forgetcode;
        }

        public String getSuspensed() {
            return suspensed;
        }

        public void setSuspensed(String suspensed) {
            this.suspensed = suspensed;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Object getCountryname() {
            return countryname;
        }

        public void setCountryname(Object countryname) {
            this.countryname = countryname;
        }

        public Object getCityname() {
            return cityname;
        }

        public void setCityname(Object cityname) {
            this.cityname = cityname;
        }

        public List<Myad> getMyads() {
            return myads;
        }

        public void setMyads(List<Myad> myads) {
            this.myads = myads;
        }

    }

    // --------------- ad model -------------------
    public static class Myad implements Parcelable {

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

        protected Myad(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            userId = in.readString();
            encodedId = in.readString();
            userRole = in.readString();
            title = in.readString();
            type = in.readString();
            used = in.readString();
            brand = in.readString();
            country = in.readString();
            city = in.readString();
            modal = in.readString();
            price = in.readString();
            phone = in.readString();
            email = in.readString();
            adDesc = in.readString();
            neogation = in.readString();
            phonecontact = in.readString();
            image = in.readString();
            suspensed = in.readString();
            createdAt = in.readString();
            views = in.readString();
        }

        public static final Creator<Myad> CREATOR = new Creator<Myad>() {
            @Override
            public Myad createFromParcel(Parcel in) {
                return new Myad(in);
            }

            @Override
            public Myad[] newArray(int size) {
                return new Myad[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            if (id == null) {
                parcel.writeByte((byte) 0);
            } else {
                parcel.writeByte((byte) 1);
                parcel.writeInt(id);
            }
            parcel.writeString(userId);
            parcel.writeString(encodedId);
            parcel.writeString(userRole);
            parcel.writeString(title);
            parcel.writeString(type);
            parcel.writeString(used);
            parcel.writeString(brand);
            parcel.writeString(country);
            parcel.writeString(city);
            parcel.writeString(modal);
            parcel.writeString(price);
            parcel.writeString(phone);
            parcel.writeString(email);
            parcel.writeString(adDesc);
            parcel.writeString(neogation);
            parcel.writeString(phonecontact);
            parcel.writeString(image);
            parcel.writeString(suspensed);
            parcel.writeString(createdAt);
            parcel.writeString(views);
        }
    }

}
