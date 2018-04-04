package com.account.create.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.account.create.model.User;
import com.account.create.service.UserRepositroy;
import com.account.create.util.CustomErrorType;


@RestController
@RequestMapping("/api")
public class CreateAccountController {
	
	public static final Logger logger=LoggerFactory.getLogger(CreateAccountController.class);
	
	@Autowired
	public UserRepositroy repository;
	
	@RequestMapping(value="/user/", method=RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = (List<User>) repository.findAll();
        for(User user:users){
        	System.out.println(user);
        }
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user/{userid}", method=RequestMethod.GET)
	public ResponseEntity<Optional<User>> getUser(@PathVariable("userid") Long userid){
		logger.info("fetching data from controller");
		Optional<User> user=repository.findById(userid);
		System.out.println(user);
		if(!user.isPresent()) {
			logger.info("no user id is found");
			 return new ResponseEntity(new CustomErrorType("User with id " + userid + " not found"), HttpStatus.NOT_FOUND);
		}else{
			return new ResponseEntity<Optional<User>>(user,HttpStatus.OK);
		}
	}
	

    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<String> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
 
        if (repository.existsById(user.getUserid())) {
            logger.error("Unable to create. A User with name {} already exist", user.getUserName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +user.getUserName() + " already exist."),HttpStatus.CONFLICT);
        }
        repository.save(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getUserid()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
}
