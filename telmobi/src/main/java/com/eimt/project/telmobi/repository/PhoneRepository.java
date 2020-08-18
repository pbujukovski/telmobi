package com.eimt.project.telmobi.repository;

import com.eimt.project.telmobi.dto.PhoneReturn;
import com.eimt.project.telmobi.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {

    List<Phone> findAll();

    Optional<PhoneReturn> findByIDEquals(Long phoneId);


}
