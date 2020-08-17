package com.challenge.mule.repository;

import com.challenge.mule.model.Indicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicatorRepository extends JpaRepository<Indicator, String> {}