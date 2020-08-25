package com.eimt.project.telmobi.repository;

import com.eimt.project.telmobi.model.Phone;
import com.eimt.project.telmobi.model.PhoneManufacturer;
import com.eimt.project.telmobi.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PhoneManufacturerRepository extends JpaRepository<PhoneManufacturer,Long> {


    Optional<PhoneManufacturer> findByNameEquals(String manufacturerName);

    Optional<PhoneManufacturer> findByPhoneModelsContaining(PhoneModel phoneModel);

    Optional<PhoneManufacturer> findByNameContaining(String name);
}
