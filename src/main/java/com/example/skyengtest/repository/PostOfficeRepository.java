package com.example.skyengtest.repository;

import com.example.skyengtest.model.Address;
import com.example.skyengtest.model.PostOffice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostOfficeRepository extends JpaRepository<PostOffice, Long> {
}
