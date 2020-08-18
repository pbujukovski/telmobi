package com.eimt.project.telmobi.service.impl;

import com.eimt.project.telmobi.dto.PhoneReturn;
import com.eimt.project.telmobi.repository.PhoneRepository;
import com.eimt.project.telmobi.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    private PhoneRepository phoneRepository;
    @Override
    public List<PhoneReturn> listAll() {
        return phoneRepository.findAll().stream().map(phone -> {
            return new PhoneReturn(
                    phone.getID(),
                    phone.getManufacturer().getName(),
                    phone.getModel().getName(),
                    phone.getRam(),
                    phone.getRom(),
                    phone.getProcessor(),
                    phone.getPrice(),
                    phone.getImageUrl()
            );

        }).collect(Collectors.toList());
    }
}
