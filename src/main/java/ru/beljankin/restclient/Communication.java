package ru.beljankin.restclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.beljankin.restclient.entity.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class Communication {
    private final String URL ="http://91.241.64.178:7081/api/users";
    private String cookie;
    private HttpHeaders httpHeaders;

    @Autowired
    private RestTemplate restTemplate;

    public void setCOOKIE(String cookie){
        this.cookie = cookie;
    }

    public List<User> showAllUsers(){
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {});
        List<User> userList = responseEntity.getBody();

        // извлеч подстроку
        String cookie = responseEntity.getHeaders().get("Set-Cookie")
                .get(0);
        setCOOKIE(cookie);
//        String cookie = responseEntity.getHeaders().get("Set-Cookie").stream().map(c -> c.JSESSIONID);
//        setCOOKIE(cookie);
        return userList;
    }


    public User getUser(Long id){
        User user = restTemplate.getForObject(URL + "/" + id, User.class);
        return user;
    }

    public void saveUser(User user){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", cookie);

        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);

        // Send request with POST method.
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, requestBody, String.class);

        System.out.println(responseEntity.getHeaders().get(0));
        System.out.println(responseEntity.getBody().toString());

//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, user, String.class);
//        System.out.println(responseEntity.getHeaders().get(0));;

    }

    public void deleteUser(Long id){

    }

    public String getCookie(){
        return this.cookie;
    }
}
