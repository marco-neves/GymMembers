package com.example.gymmembersapp.util;

public class Plan {
    int id;
    String price;
    String name;

    public Plan(int id) {
        this.id = id;
        if(id==1){
            price = "$7.99";
            name = "Regular";
        }else if(id==2){
            price = "$13.99";
            name = "Plus";
        }else if(id==3){
            price = "$24.99";
            name = "Premium";
        }else{
            price = "";
            name = "";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        String toReturn = "";
        toReturn+=name+" Plan";
        toReturn+="\n";
        toReturn+=price+" montly";
        return toReturn;
    }
}
