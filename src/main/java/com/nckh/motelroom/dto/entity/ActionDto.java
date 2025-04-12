package com.nckh.motelroom.dto.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nckh.motelroom.model.enums.ActionName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionDto implements Serializable {
    private Long id;

    private String email;

    private String fullName;

    private ActionName action;

    private String postTitle;

    private Long postId;

    private String motel;

    private Boolean isRead;

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private LocalDateTime time;

}