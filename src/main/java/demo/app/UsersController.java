package demo.app;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import demo.entity.User;
import demo.repository.UserRepository;

@RestController
@RequestMapping("demo/users")
public class UsersController {

	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(path = "/", method = RequestMethod.GET)
	@Transactional
	public List<User> findAll(){
		List<User>list = userRepository.findAll();
		return userRepository.findAll();
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	@Transactional
	public List<User>findById(@PathVariable("id") int id){
		 return userRepository.findById(id);
	}
	
	@RequestMapping(path = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public User create(@RequestBody User user){
		 return userRepository.save(user);
	}
	
}
