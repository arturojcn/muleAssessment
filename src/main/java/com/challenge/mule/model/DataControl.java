package com.challenge.mule.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "data_control")
public class DataControl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "folder_name")
    private String folderName;
    @Column(name = "status")
    private Boolean isActive = true;
    @CreationTimestamp
    @Column(name = "creation_time")
    private Date creationTime;

    public DataControl() {}

    public DataControl(String folderName) {
        this.folderName = folderName;
    }
}
