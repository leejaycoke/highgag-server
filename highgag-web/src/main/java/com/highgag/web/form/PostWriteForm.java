package com.highgag.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class PostWriteForm {

    @NotBlank(message = "제목을 입력해주세요.")
    @Length(min = 1, max = 50, message = "제목은 50자까지 가능합니다.")
    private String title;

    @Length(max = 500, message = "내용은 500자까지 입력할 수 있습니다.")
    private String content;

}
