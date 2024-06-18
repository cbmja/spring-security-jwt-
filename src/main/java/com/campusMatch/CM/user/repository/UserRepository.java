package com.campusMatch.CM.user.repository;



import com.campusMatch.CM.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {

    //Boolean existByUsername(String username);
    User findByUsername(String username);
}
