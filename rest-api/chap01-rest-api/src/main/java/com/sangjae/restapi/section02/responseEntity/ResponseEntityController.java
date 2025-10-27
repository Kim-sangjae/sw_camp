package com.sangjae.restapi.section02.responseEntity;

import org.apache.catalina.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/entity")
public class ResponseEntityController {

    private List<UserDTO> users;


    public ResponseEntityController(){
        users = new ArrayList<>();
        users.add(new UserDTO(1,"user01","pass01","유관순"));
        users.add(new UserDTO(2,"user02","pass02","홍길동"));
        users.add(new UserDTO(3,"user03","pass03","이순신"));
    }

    // 1. 모든 유저 정보 조회
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){
        // 응답 헤더 설정 : JSON 응답이 default 이기는 하나 변경이 필요한경우
        // HttpHeaders 설정 변경

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application","json", StandardCharsets.UTF_8));

        // 응답 바디 설정
        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("users",users);
        ResponseMessage responseMessage = new ResponseMessage(
                200,"전체 유저 조회",responseMap
        );

        // GET 방식의 요청은 성공 200 , 실패 404 처리
        return new ResponseEntity<>(responseMessage,httpHeaders, HttpStatus.OK);
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


    // 3. 새로운 유저 생성
    @PostMapping("/users")
    public ResponseEntity<Void> registerUser(@RequestBody UserDTO userDTO){

        // db 없이 메모리상에 데이터 저장
        int lastUserNo = users.get(users.size() - 1).getNo();
        userDTO.setNo(lastUserNo+1);
        users.add(userDTO);
        // Post 방식은 성공시 201 created 응답을 사용한다.
        // .ok() : 200 , .created() : 201
        // URI.create( 응답 성공시 다른 해당 주소로 요청할 수 있다는걸 알려 준다 )
        return ResponseEntity.created(URI.create("/entity/users/" + (lastUserNo + 1))).build();
    }

    @PutMapping("/users/{userNo}")
    public ResponseEntity<Void> modifyUser(@PathVariable int userNo, @RequestBody UserDTO userDTO){

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo)
                .findFirst().get();
        foundUser.setPwd(userDTO.getPwd());
        foundUser.setName(userDTO.getName());

        // PUT 방식이 성공하면 200 or 204 No content 등으로 응답한다
        // 실패시 400
//        return ResponseEntity.noContent().build();

        // URI 로 재요청을 보내는 방식
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("/entity/users/" + userNo))
                .build();
    }

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<Void> removeUSer(@PathVariable int userNo){
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo)
                .findFirst().get();

        users.remove(foundUser);

        // delete 성공시 204번 반환
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create("/entity/users")).build();
    }
}


