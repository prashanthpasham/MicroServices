package com.prashanth.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.prashanth.entity.Users;
@Repository
public interface UsersRepository extends MongoRepository<Users, Integer> {
public Users findByUserNameAndPassword(String userName,String pwd);
	

}
