package com.eimt.project.telmobi.service.impl;

import com.eimt.project.telmobi.dto.PhoneCreation;
import com.eimt.project.telmobi.dto.PhoneReturn;
import com.eimt.project.telmobi.model.Phone;
import com.eimt.project.telmobi.model.PhoneManufacturer;
import com.eimt.project.telmobi.model.PhoneModel;
import com.eimt.project.telmobi.model.exception.ResourceNotFoundException;
import com.eimt.project.telmobi.repository.*;
import com.eimt.project.telmobi.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {
    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PhoneModelRepository phoneModelRepository;
    private final PhoneManufacturerRepository manufacturerRepository;

    public PhoneServiceImpl(PhoneRepository phoneRepository, UserRepository userRepository, RoleRepository roleRepository, PhoneModelRepository phoneModelRepository, PhoneManufacturerRepository manufacturerRepository) {
        this.phoneRepository = phoneRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.phoneModelRepository = phoneModelRepository;
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public List<PhoneReturn> listAll() {
        return phoneRepository.findAll().stream().map(phone -> {
            PhoneModel model = this.phoneModelRepository.getOne(phone.getID());
            return mapReturn(phone,model);

        }).collect(Collectors.toList());
    }

    @Override
    public List<PhoneReturn> listPhoneByManufacturer(String manufacturerName) {
        PhoneManufacturer phoneManufacturer = this.manufacturerRepository.findByNameContaining(manufacturerName).orElseThrow(RuntimeException::new);
        return this.phoneRepository.findAllByManufacturer(phoneManufacturer).stream().map(phone -> {
                    PhoneModel model = this.phoneModelRepository.getOne(phone.getID());
                    return mapReturn(phone, model);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public List<PhoneReturn> listPhonesPriceBetween(double lowPrice, double highPrice) {
        return this.phoneRepository.findAllByPriceBetweenOrderByPrice(lowPrice,highPrice).stream().map(phone -> {
                    PhoneModel model = this.phoneModelRepository.getOne(phone.getID());
                    return mapReturn(phone, model);
                }
        ).sorted().collect(Collectors.toList());
    }

    @Override
    public List<PhoneReturn> listPopularPhones() {
        return null;
    }

    @Override
    public Phone createPhone(PhoneCreation phoneCreate) {
        PhoneManufacturer manufacturer = this.manufacturerRepository.findByNameEquals(phoneCreate.getManufacturer()).orElseGet(()->
                manufacturerRepository.save(new PhoneManufacturer(phoneCreate.getManufacturer(),new ArrayList<PhoneModel>()))
        );
        PhoneModel model = this.phoneModelRepository.findPhoneModelByName(phoneCreate.getModel()).orElseGet(()->
            phoneModelRepository.save(new PhoneModel(phoneCreate.getModel()))
        );

        if(manufacturer.getPhoneModels().indexOf(model)==-1){
            manufacturer.getPhoneModels().add(model);
            this.manufacturerRepository.save(manufacturer);

        }
        return this.phoneRepository.save(new Phone(
                manufacturer,
                model.getId(),
                phoneCreate.getRam(),
                phoneCreate.getRom(),
                phoneCreate.getProcessor(),
                phoneCreate.getPrice(),
                phoneCreate.getImageUrl()
        ));
    }

    @Override
    public PhoneReturn returnPhone(Long phoneId) {
        Phone phone = phoneRepository.findByIDEquals(phoneId).orElseThrow(()->new ResourceNotFoundException("Phone","Phone id",phoneId));
        PhoneModel phoneModel = phoneModelRepository.findById(phone.getModelId()).orElseThrow(()->new ResourceNotFoundException("Phone","Phone id",phoneId));
        return mapReturn(phone,phoneModel);
    }

    @Override
    public List<PhoneReturn> searchPhones(String name) {
        return new ArrayList<PhoneReturn>();
    }

    @Override
    public void deletePhone(Long phoneId) {
        this.phoneRepository.deleteById(phoneId);
    }

    public static PhoneReturn mapReturn(Phone phone,PhoneModel model){
        return new PhoneReturn(
                phone.getID(),
                phone.getManufacturer().getName(),
                model.getName(),
                phone.getRam(),
                phone.getRom(),
                phone.getProcessor(),
                phone.getPrice(),
                phone.getImageUrl()
        );
    }
}
