package com.eimt.project.telmobi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneReturn implements Comparable {

    @NotNull
    private Long ID;

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

    @Override
    public int compareTo(Object o) {
        PhoneReturn other = (PhoneReturn)o;
        if(this.price>other.price)
            return 1;
        if(this.price<other.price)
            return -1;
        return 0;
    }
}
