    package com.nckh.motelroom.service.impl;

    import com.nckh.motelroom.dto.entity.ActionDto;
    import com.nckh.motelroom.exception.DataNotFoundException;
    import com.nckh.motelroom.model.Action;
    import com.nckh.motelroom.model.Post;
    import com.nckh.motelroom.model.User;
    import com.nckh.motelroom.model.enums.ActionName;
    import com.nckh.motelroom.repository.ActionRepository;
    import com.nckh.motelroom.repository.custom.CustomActionQuery;
    import com.nckh.motelroom.repository.custom.CustomPostQuery;
    import com.nckh.motelroom.service.ActionService;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.*;
    import org.springframework.data.jpa.domain.Specification;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Optional;

    @Service
    @RequiredArgsConstructor
    @Slf4j
    public class ActionServiceImp implements ActionService {

        private final ActionRepository actionRepository;

        @Override
        public void createAction(Post post, User user, ActionName actionName) {
            try {
                Action action = new Action(post, user, actionName);
                actionRepository.save(action);
            } catch (Exception e) {
                log.error("Lỗi khi tạo hoạt động: {}", e.getMessage());
                throw new RuntimeException("Lỗi khi tạo hoạt động: " + e.getMessage());
            }
        }

        @Override
        public Page<Action> getAction(CustomActionQuery.ActionFilterParam param, PageRequest pageRequest) {
            try {
                Specification<Action> specification = CustomActionQuery.getFilterAction(param);
                return actionRepository.findAll(specification, pageRequest);
            }catch (Exception e){
                throw new DataNotFoundException("Không có bài viết nào được tìm thấy! " + e.getMessage());
            }
        }


        @Override
        public void markActionAsRead(Long actionId) {
            Optional<Action> actionOpt = actionRepository.findById(actionId);
            if (actionOpt.isPresent()) {
                Action action = actionOpt.get();
                action.setIsRead(true);
                actionRepository.save(action);
            } else {
                throw new DataNotFoundException("Không tìm thấy hoạt động với id: " + actionId);
            }
        }

    }
