package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.ActionDto;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.model.enums.ActionName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActionService {
    void createAction(Post post, User user, ActionName actionName);
    Page<ActionDto> getAction(int age);
    Page<ActionDto> getActionByApprover(Long id, int page);
}
