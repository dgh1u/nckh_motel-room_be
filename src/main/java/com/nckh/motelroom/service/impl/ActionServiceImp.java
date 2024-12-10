package com.nckh.motelroom.service.impl;

import com.nckh.motelroom.dto.entity.ActionDto;
import com.nckh.motelroom.model.Action;
import com.nckh.motelroom.model.Post;
import com.nckh.motelroom.model.User;
import com.nckh.motelroom.model.enums.ActionName;
import com.nckh.motelroom.repository.ActionRepository;
import com.nckh.motelroom.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImp implements ActionService {

    @Autowired
    private ActionRepository actionRepository;

    @Override
    public void createAction(Post post, User user, ActionName actionName) {
        Action action = new Action(post, user, actionName);
        actionRepository.save(action);
    }

    @Override
    public Page<ActionDto> getAction(int age) {
        Page<Action> actionPage = actionRepository.findAll(PageRequest.of(age, 20, Sort.by("time").descending()));
        return actionPage.map(action ->
                new ActionDto(action.getId(), action.getUser().getFullName(), action.getAction(), action.getPost().getTitle(), action.getPost().getId(), action.getTime()));
    }

    @Override
    public Page<ActionDto> getActionByApprover(Long id, int page) {
        Page<Action> actionPage = actionRepository.findAllByUser_Id(id, PageRequest.of(page, 20, Sort.by("time").descending()));
        Page<ActionDto> actionDTOPage = actionPage.map(action ->
                new ActionDto(action.getId(), action.getUser().getFullName(), action.getAction(), action.getPost().getTitle(), action.getPost().getId(), action.getTime())
        );
        return actionDTOPage;
    }
}
