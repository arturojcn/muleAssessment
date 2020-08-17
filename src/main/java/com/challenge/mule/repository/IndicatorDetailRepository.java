package com.challenge.mule.repository;

import com.challenge.mule.model.IndicatorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndicatorDetailRepository extends JpaRepository<IndicatorDetail, Long> {}