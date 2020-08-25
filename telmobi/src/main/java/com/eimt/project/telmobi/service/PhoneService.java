package com.eimt.project.telmobi.service;

import com.eimt.project.telmobi.dto.PhoneCreation;
import com.eimt.project.telmobi.dto.PhoneReturn;
import com.eimt.project.telmobi.model.Phone;
import  com.eimt.project.telmobi.security.UserPrincipal;


import java.util.List;

import java.util.Optional;
public interface PhoneService {

    public List<PhoneReturn> listAll();


    List<PhoneReturn> listPhoneByManufacturer(String manufacturerName);

    List<PhoneReturn> listPhonesPriceBetween(double lowPrice,double highPrice);

    List<PhoneReturn> listPopularPhones();

    Phone createPhone(PhoneCreation phoneCreate);

    PhoneReturn returnPhone(Long phoneId);
    List<PhoneReturn> searchPhones(String name);

    void deletePhone(Long phoneId);

}
