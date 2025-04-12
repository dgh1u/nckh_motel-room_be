package com.nckh.motelroom.dto.request.comment;

import com.nckh.motelroom.repository.custom.CustomCommentQuery;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class GetCommentRequest extends CustomCommentQuery.CommentFilterParam {

    @Min(value = 0, message = "Số trang phải bắt đầu từ 0")
    private int start = 0;

    @Range(min = 5, max = 50, message = "Số lượng bình luận trong một trang là từ 5 đến 50")
    private int limit = 10;
}
