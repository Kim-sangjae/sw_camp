package com.sangjae.restapi.section04.hateoas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
