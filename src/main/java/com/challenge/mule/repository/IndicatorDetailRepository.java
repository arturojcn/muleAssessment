package com.challenge.mule.repository;

import com.challenge.mule.model.IndicatorDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
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

    @Query(value = "SELECT * " +
            "FROM indicator_details " +
            "WHERE indicator_id IN (:indicator_id) AND year " +
            "BETWEEN :startYear AND :endYear ORDER BY country_id ASC, year ASC", nativeQuery = true)
    List<IndicatorDetail> findAllByRangeDateAndIndicators(@Param("indicator_id") List<String> indicator_id,
                                          @Param("startYear") int startYear,
                                          @Param("endYear") int endYear);

    @Modifying
    @Query(value = "UPDATE indicator_details set value = :value where id = :id", nativeQuery = true)
    void updateValueById(@Param("value") BigDecimal value, @Param("id") Long id);
}