package com.nckh.motelroom.service;

import com.nckh.motelroom.dto.entity.ActionDto;
import com.nckh.motelroom.model.Action;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.model.enums.ActionName;
import com.nckh.motelroom.repository.custom.CustomActionQuery;
import com.nckh.motelroom.repository.custom.CustomPostQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ActionService {
    void createAction(Post post, User user, ActionName actionName);

    Page<Action> getAction(CustomActionQuery.ActionFilterParam param, PageRequest pageRequest);

    void markActionAsRead(Long actionId);

}
