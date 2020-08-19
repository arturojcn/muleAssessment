package com.challenge.mule.repository;

import com.challenge.mule.model.DataControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataControlRepository extends JpaRepository<DataControl, Long> {
    @Query(value = "SELECT * FROM data_control WHERE status=true ORDER BY creation_time ASC;", nativeQuery = true)
    List<DataControl> findAllIsActive();

    @Query(value = "SELECT * FROM data_control WHERE status=true ORDER BY creation_time DESC LIMIT 1;", nativeQuery = true)
    DataControl getLatestActiveFolder();
}
