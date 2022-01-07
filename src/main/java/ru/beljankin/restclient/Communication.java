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
import java.util.Map;

@Component
public class Communication {
    private final String URL ="http://91.241.64.178:7081/api/users";
    private String cookie;
    private String respose;
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

//        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);

        // Send request with POST method.
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, requestBody, String.class);
        respose = responseEntity.getBody();
//        System.out.println(responseEntity.getHeaders().get(0));
        System.out.println("1 :" + responseEntity.getBody());

//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL, user, String.class);
//        System.out.println(responseEntity.getHeaders().get(0));;

    }

    public void editUser(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", cookie);

//        RestTemplate restTemplate = new RestTemplate();

        // Data attached to the request.
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);

        // Send request with POST method.
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT,requestBody, String.class);
        respose += responseEntity.getBody();
        System.out.println("2 :" + responseEntity.getBody());

    }

    public void deleteUser(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
        // Request to return JSON format
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", cookie);



//
//
//
//        Map<String, Long> params = new HashMap<>();
//        params.put("id", id);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Cookie", cookie);

//        HttpEntity<String> entity = new HttpEntity(params, headers);

//        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity(headers);


        // Send request with POST method.
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class);
        respose += responseEntity.getBody();
        System.out.println("3 :" + responseEntity.getBody());
        System.out.println("final :" + respose);

    }

    public String getCookie(){
        return this.cookie;
    }
}
