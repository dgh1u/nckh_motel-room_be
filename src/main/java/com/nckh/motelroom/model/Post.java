package com.nckh.motelroom.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "approved")
    private Boolean approved;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "create_at")
    private Instant createAt;

    @Column(name = "del")
    private Boolean del;

    @Column(name = "last_update")
    private Instant lastUpdate;

    @Column(name = "not_approved")
    private Boolean notApproved;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "accomodation_id")
    private Accomodation accomodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

}