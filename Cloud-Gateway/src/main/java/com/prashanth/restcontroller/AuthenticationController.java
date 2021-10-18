package com.prashanth.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.prashanth.configuration.JwtUtil;
import com.prashanth.dao.DatabaseSeqRepository;
import com.prashanth.dao.UsersRepository;
import com.prashanth.entity.DatabaseSequence;
import com.prashanth.entity.Users;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	@Autowired
	private UsersRepository userRepo;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private DatabaseSeqRepository dataBaseRepo;

	@PostMapping(value = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<ObjectNode> login(@RequestBody Users users) {
		ObjectNode result = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.createObjectNode();
			result.put("error", "");
			result.put("token", "");
			Users user = userRepo.findByUserNameAndPassword(users.getUserName(), users.getPassword());
			if (user != null) {
				String token = jwtUtil.generateToken(user.getUserName());
				result.put("token", token);
				return new ResponseEntity(result, HttpStatus.ACCEPTED);
			} else {
				result.put("error", "Invalid Credentials");
				return new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
	}
	@PostMapping(value = "/create-user", consumes = "application/json")
	public ResponseEntity<String> createUser(@RequestBody Users users) {
		try {
			DatabaseSequence dataSeq = dataBaseRepo.findBySequenceName(Users.SEQUENCE_NAME);
			if (dataSeq == null) {
				dataSeq = new DatabaseSequence();
				dataSeq.setId(1);
				dataSeq.setSequenceName(Users.SEQUENCE_NAME);
			} else {
				dataSeq.setId(dataSeq.getId() + 1);
			}
			dataBaseRepo.save(dataSeq);
			users.setUserId(dataSeq.getId());
			Users user = userRepo.insert(users);
			return new ResponseEntity("success", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity("failure", HttpStatus.BAD_REQUEST);
	}
}
