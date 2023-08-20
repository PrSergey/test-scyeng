package com.example.skyengtest.repository;

import com.example.skyengtest.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
