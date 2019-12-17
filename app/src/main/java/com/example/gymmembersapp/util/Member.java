package com.example.gymmembersapp.util;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {

    private String id;
    private String name;
    private int plan;

    public Member(String id, String name, int plan) {
        this.id = id;
        this.name = name;
        this.plan = plan;
    }

    public Member(String concatenatedName){
        String[] info = concatenatedName.split("\\*");
        id = info[0];
        if(info[1]==null){
            name="";
        }else {
            name = info[1];
        }
        plan = Integer.parseInt(info[2]);
    }

    //constructor for the parcelable
    public Member(Parcel in){
        this.id = in.readString();
        this.name = in.readString();
        this.plan = in.readInt();
    }


    //for saving the member to the file
    public String toSaveString(){
        String toReturn = "";
        toReturn+=id+"*"+name+"*"+plan;
        return toReturn;
    }

    public String toString(){
        String toReturn = "";
        toReturn+=id+" "+name+" "+plan;
        return toReturn;
    }

    //gettters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getPlan() {
        return plan;
    }
    public void setPlan(int plan) {
        this.plan = plan;
    }

    //parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.plan);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Object createFromParcel(Parcel source) {
            return new Member(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new Member[size];
        }
    };
}
