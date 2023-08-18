package com.example.skyengtest.repository;

import com.example.skyengtest.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.hibernate5.HibernateTemplate;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByItem_Id(Long itemId);
}
