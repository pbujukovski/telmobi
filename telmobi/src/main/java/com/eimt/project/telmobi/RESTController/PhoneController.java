package com.eimt.project.telmobi.RESTController;

import com.eimt.project.telmobi.dto.ApiResponse;
import com.eimt.project.telmobi.dto.PhoneCreation;
import com.eimt.project.telmobi.dto.PhoneReturn;
import com.eimt.project.telmobi.model.Phone;
import com.eimt.project.telmobi.security.CurrentUser;
import com.eimt.project.telmobi.security.UserPrincipal;
import com.eimt.project.telmobi.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/phone")
public class PhoneController {

    @Autowired
    PhoneService phoneService;

    @PostMapping("/create")
    public ResponseEntity<?> createPhone(@Valid @RequestBody PhoneCreation phoneCreate){

        Phone result=phoneService.createPhone(phoneCreate);
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/phone/{phoneId}")
                .buildAndExpand(result.getID()).toUri();
        return ResponseEntity.created(location).body(new ApiResponse(true, "Phone created successfully!"));
    }
    @GetMapping("/{phoneId}")
    public PhoneReturn getPhone(@PathVariable(value="phoneId") Long phoneId){
        return phoneService.returnPhone(phoneId);
    }
    @GetMapping("/all/manufacturer/{phoneManufacturer}")
    public List<PhoneReturn> findPhoneByManufacturer(@PathVariable(value="phoneManufacturer") String phoneManufacturer){
        return this.phoneService.listPhoneByManufacturer(phoneManufacturer);
    }
    @GetMapping("/all/price/")
    public List<PhoneReturn> findPhoneByPrice(@RequestParam String minPrice,@RequestParam String maxPrice){

        return this.phoneService.listPhonesPriceBetween(Double.parseDouble(minPrice),Double.parseDouble(maxPrice));
    }
    @DeleteMapping("/{phoneId}")
    public ResponseEntity<?> deletePhone(@PathVariable(value="phoneId") Long phoneID){
        this.phoneService.deletePhone(phoneID);
        return ResponseEntity.ok(new ApiResponse(true,"Phone deleted successfully"));
    }

    @GetMapping("/all")
    public List<PhoneReturn> findAll(){
        return this.phoneService.listAll();
    }
}
