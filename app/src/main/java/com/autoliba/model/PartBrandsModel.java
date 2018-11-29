package com.autoliba.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ma7MouD on 11/15/2018.
 */

public class PartBrandsModel {
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

    // -------------- Message -----------------
    public class Message {

        @SerializedName("parts")
        @Expose
        private List<Part> parts = null;

        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }

    }

    // ------------ One Part ------------
    public static class Part implements Parcelable {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("parent")
        @Expose
        private String parent;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("price")
        @Expose
        private Object price;
        @SerializedName("partinfo")
        @Expose
        private List<Partinfo> partinfo = null;

        protected Part(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            name = in.readString();
            parent = in.readString();
            image = in.readString();
        }

        public static final Creator<Part> CREATOR = new Creator<Part>() {
            @Override
            public Part createFromParcel(Parcel in) {
                return new Part(in);
            }

            @Override
            public Part[] newArray(int size) {
                return new Part[size];
            }
        };

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

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Object getPrice() {
            return price;
        }

        public void setPrice(Object price) {
            this.price = price;
        }

        public List<Partinfo> getPartinfo() {
            return partinfo;
        }

        public void setPartinfo(List<Partinfo> partinfo) {
            this.partinfo = partinfo;
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
            parcel.writeString(name);
            parcel.writeString(parent);
            parcel.writeString(image);
        }
    }

    // ------------- Part Info -----------
    public static class Partinfo implements Parcelable {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("parent")
        @Expose
        private String parent;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("price")
        @Expose
        private String price;

        protected Partinfo(Parcel in) {
            if (in.readByte() == 0) {
                id = null;
            } else {
                id = in.readInt();
            }
            name = in.readString();
            parent = in.readString();
            price = in.readString();
        }

        public static final Creator<Partinfo> CREATOR = new Creator<Partinfo>() {
            @Override
            public Partinfo createFromParcel(Parcel in) {
                return new Partinfo(in);
            }

            @Override
            public Partinfo[] newArray(int size) {
                return new Partinfo[size];
            }
        };

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

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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
            parcel.writeString(name);
            parcel.writeString(parent);
            parcel.writeString(price);
        }
    }
}
