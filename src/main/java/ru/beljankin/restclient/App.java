package ru.beljankin.restclient;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpHeaders;
import ru.beljankin.restclient.configuration.MyConfig;
import ru.beljankin.restclient.entity.User;

import java.net.HttpCookie;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        User user = new User();
        user.setId(3L);
        user.setName("James");
        user.setLastName("Brown");
        user.setAge((byte) 10);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        Communication communication = context.getBean("communication", Communication.class);

        List<User> allUsers = communication.showAllUsers();

        System.out.println(allUsers);
//        System.out.println(communication.getCookie());

        communication.saveUser(user);

        user.setName("Thomas");
        user.setLastName("Shelby");
        communication.editUser(user);

        communication.deleteUser(3L);
    }
}
