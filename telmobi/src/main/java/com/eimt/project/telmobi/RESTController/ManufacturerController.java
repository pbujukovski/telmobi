package com.eimt.project.telmobi.RESTController;

import com.eimt.project.telmobi.repository.PhoneManufacturerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/manufacturer")
public class ManufacturerController {
    @Autowired
    PhoneManufacturerRepository phoneManufacturerRepository;

    @GetMapping("/all")
    public List<String> getAll() {
        return phoneManufacturerRepository.findAll().stream().map(manufacturer -> manufacturer.getName()).collect(Collectors.toList());
    }
}




