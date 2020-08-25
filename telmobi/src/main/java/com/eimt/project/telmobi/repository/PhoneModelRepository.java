package com.eimt.project.telmobi.repository;

import com.eimt.project.telmobi.model.PhoneModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneModelRepository extends JpaRepository<PhoneModel,Long> {

    Optional<PhoneModel> findPhoneModelByName(String phoneModel);

}
