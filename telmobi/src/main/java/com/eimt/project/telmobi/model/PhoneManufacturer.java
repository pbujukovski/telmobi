package com.eimt.project.telmobi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="manufacturers")
public class PhoneManufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String name;

    @OneToMany
    private List<PhoneModel> phoneModels=new ArrayList<>();

    public PhoneManufacturer() {
    }
    public PhoneManufacturer(Long Id, String name, List<PhoneModel> phoneModels){
        this.Id = Id;
        this.name = name;
        this.phoneModels=phoneModels;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PhoneModel> getPhoneModels() {
        return phoneModels;
    }

    public void setPhoneModels(List<PhoneModel> phoneModels) {
        this.phoneModels = phoneModels;
    }
}
