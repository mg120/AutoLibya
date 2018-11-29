package com.autoliba.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ma7MouD on 11/8/2018.
 */

public class UpdateProfileModel {

    @SerializedName("success")
    @Expose
    private Boolean success ;
    @SerializedName("data")
    @Expose
    private String data ;
    @SerializedName("message")
    @Expose
    private String message ;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // --------------- Message ----------------------------
    public static class Message implements Parcelable{

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
        private Object bio;
        @SerializedName("firebase_token")
        @Expose
        private String firebaseToken;
        @SerializedName("device_id")
        @Expose
        private String deviceId;
        @SerializedName("forgetcode")
        @Expose
        private Object forgetcode;
        @SerializedName("suspensed")
        @Expose
        private String suspensed;
        @SerializedName("role")
        @Expose
        private String role;

        protected Message(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            encodedId = in.readString();
            firstname = in.readString();
            lastname = in.readString();
            username = in.readString();
            email = in.readString();
            country = in.readString();
            area = in.readString();
            phone = in.readString();
            address = in.readString();
            password = in.readString();
            image = in.readString();
            firebaseToken = in.readString();
            deviceId = in.readString();
            suspensed = in.readString();
            role = in.readString();
        }

        public static final Creator<Message> CREATOR = new Creator<Message>() {
            @Override
            public Message createFromParcel(Parcel in) {
                return new Message(in);
            }

            @Override
            public Message[] newArray(int size) {
                return new Message[size];
            }
        };

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

        public Object getBio() {
            return bio;
        }

        public void setBio(Object bio) {
            this.bio = bio;
        }

        public String getFirebaseToken() {
            return firebaseToken;
        }

        public void setFirebaseToken(String firebaseToken) {
            this.firebaseToken = firebaseToken;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public Object getForgetcode() {
            return forgetcode;
        }

        public void setForgetcode(Object forgetcode) {
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
            parcel.writeString(encodedId);
            parcel.writeString(firstname);
            parcel.writeString(lastname);
            parcel.writeString(username);
            parcel.writeString(email);
            parcel.writeString(country);
            parcel.writeString(area);
            parcel.writeString(phone);
            parcel.writeString(address);
            parcel.writeString(password);
            parcel.writeString(image);
            parcel.writeString(firebaseToken);
            parcel.writeString(deviceId);
            parcel.writeString(suspensed);
            parcel.writeString(role);
        }
    }

}
