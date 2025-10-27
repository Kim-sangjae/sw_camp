package com.sangjae.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Schema(description = "회원 정보 DTO")
public class UserDTO {
    @Schema(description = "회원번호")
    private int no;
    @Schema(description = "회원아이디")
    private String id;
    @Schema(description = "회원비밀번호")
    private String pwd;
    @Schema(description = "회원이름")
    private String name;
}
