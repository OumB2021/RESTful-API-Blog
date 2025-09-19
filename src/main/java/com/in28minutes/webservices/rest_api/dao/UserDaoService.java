package com.in28minutes.webservices.rest_api.dao;

import com.in28minutes.webservices.rest_api.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class UserDaoService {

    private static List<User> users = new ArrayList<>();
    private static int usersCount = 0;

    static {
        users.add(new User(++usersCount, "Adam", LocalDate.now().minusYears(30)));
        users.add(new User(++usersCount, "Oumar", LocalDate.now().minusYears(20)));
        users.add(new User(++usersCount, "Sadia", LocalDate.now().minusYears(10)));
        users.add(new User(++usersCount, "Maman", LocalDate.now().minusYears(60)));
        users.add(new User(++usersCount, "Rama", LocalDate.now().minusYears(5)));

    }

     public List<User> findAll(){
        return users;
     }

     public User save(User user){
        user.setId(++usersCount);
        users.add(user);
        return user;
     }

     public User findOne(int id){
         Predicate<? super User> predicate = user -> user.getId().equals(id);
         return users.stream().filter(predicate).findFirst().orElse(null);
     }

     public void deleteById(int id){
         Predicate<? super User> predicate = user -> user.getId().equals(id);
         users.removeIf(predicate);
     }
}
