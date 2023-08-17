package com.example.skyengtest.repository;

import com.example.skyengtest.model.PostalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostalItemRepository extends JpaRepository<PostalItem, Long> {
}
