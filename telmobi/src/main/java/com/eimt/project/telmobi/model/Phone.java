package com.eimt.project.telmobi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @NotNull
    @OneToOne
    private PhoneManufacturer manufacturer;

    @NotNull
    @OneToOne
    private PhoneModel model;

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

    public Phone() {
    }

    public Phone(Long ID, PhoneManufacturer phoneManufacturer, PhoneModel phoneModel,int ram, int rom, String processor,double price,String imageUrl){
        this.ID= ID;
        this.manufacturer=phoneManufacturer;
        this.model=phoneModel;
        this.ram=ram;
        this.rom=rom;
        this.processor=processor;
        this.price=price;
        this.imageUrl= imageUrl;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public PhoneManufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(PhoneManufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public PhoneModel getModel() {
        return model;
    }

    public void setModel(PhoneModel model) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
