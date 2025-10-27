package com.sangjae.restapi.section04.hateoas;

import com.sangjae.restapi.section02.responseEntity.ResponseMessage;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/hateoas")
public class HateoasController {

    private List<UserDTO> users;

    public HateoasController(){
        users = new ArrayList<>();
        users.add(new UserDTO(1,"user01","pass01","유관순"));
        users.add(new UserDTO(2,"user02","pass02","홍길동"));
        users.add(new UserDTO(3,"user03","pass03","이순신"));
    }

    // 1. 모든 유저 정보 조회
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
       // Hateoas 설정
        List<EntityModel<UserDTO>> usersWithRel = users.stream().map(
                user -> EntityModel.of(
                        user,
                        linkTo(methodOn(HateoasController.class).findUserByNo(user.getNo())).withSelfRel(),
                        linkTo(methodOn(HateoasController.class).findAllUsers()).withRel("users")
                )
        ).toList();

        // 응답 바디 설정
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("users",usersWithRel);

        ResponseMessage responseMessage = new ResponseMessage(
                200,"전체 유저 조회",responseMap
        );

        // GET 방식의 요청은 성공 200 , 실패 404 처리
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }


    // 특정 유저 조회
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {
        // 응답 헤더 설정 : JSON 응답이 default 이기는 하나 변경이 필요한경우
        // HttpHeaders 설정 변경
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        // 응답 바디 설정
        Map<String, Object> responseMap = new HashMap<>();
        UserDTO foundUser = users.stream().filter(userDTO -> userDTO.getNo() == userNo)
                .findFirst().get();
        responseMap.put("user", foundUser);
        ResponseMessage responseMessage = new ResponseMessage(
                200, "특정 유저 조회", responseMap
        );

        return ResponseEntity.ok().headers(httpHeaders).body(responseMessage);
    }
}
