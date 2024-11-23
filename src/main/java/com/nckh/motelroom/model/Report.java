package com.nckh.motelroom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "report")
public class Report {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "file_path")
    private String filePath;

    @Lob
    @Column(name = "filter_criteria")
    private String filterCriteria;

}