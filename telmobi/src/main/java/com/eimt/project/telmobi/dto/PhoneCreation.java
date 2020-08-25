package com.eimt.project.telmobi.dto;

import java.lang.String;
import javax.validation.constraints.NotNull;

public class PhoneCreation {

    @NotNull
    private String manufacturer;

    @NotNull
    private String model;

    @NotNull
    private int ram;

    @NotNull
    private int rom;

    @NotNull
    private String processor;

    @NotNull
    private double price;

    @NotNull
    private String imageUrl;



    public PhoneCreation( String manufacturer, String model, int ram, int rom, String processor,double price,String imageUrl) {

        this.manufacturer = manufacturer;
        this.model = model;
        this.ram = ram;
        this.rom = rom;
        this.processor = processor;
        this.price = price;
        this.imageUrl = imageUrl;

    }


    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturern(String manufacturer) {
        this.manufacturer = manufacturer;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public int getRom() {
        return rom;
    }

    public void setRom(int rom) {
        this.rom = rom;
    }


    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public double getPrice(){
        return  price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
