package com.bos.bos.repository;


import com.bos.bos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//Class:4
public interface UserRepository extends JpaRepository<User, Long> {

    //Method:1
    User findByUsername(String username);

}
