package com.challenge.mule.repository;

import com.challenge.mule.model.IndicatorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicatorDetailRepository extends JpaRepository<IndicatorDetail, Long> {

    @Query(value = "SELECT * " +
            "FROM indicator_details " +
            "WHERE country_id=:country_id AND indicator_id=:indicator_id AND year " +
            "BETWEEN :startYear AND :endYear ORDER BY year ASC", nativeQuery = true)
    List<IndicatorDetail> findAllByUnique(@Param("country_id") String country_id,
                                          @Param("indicator_id") String indicator_id,
                                          @Param("startYear") int startYear,
                                          @Param("endYear") int endYear);
}