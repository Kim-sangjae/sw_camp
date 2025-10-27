package com.sangjae.restapi.section02.responseEntity;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class UserDTO {
    private int no;
    private String id;
    private String pwd;
    private String name;
}
