package com.challenge.mule.repository;

import com.challenge.mule.model.IncomeGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeGroupRepository extends JpaRepository<IncomeGroup, Long> {
    IncomeGroup findByName(String groupName);
}