package my.com.api.user.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import my.com.api.user.dto.UserRequestDTO;
import my.com.api.user.dto.UserResponseDTO;
import my.com.api.user.services.UserService;

@RestController
@RequestMapping("/v1/users")
public class UserResource {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "List all User details")
	@GetMapping("")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

		return ResponseEntity.ok(userService.getAllUsers());
	}

	
	
	@ApiOperation(value = "Create User")
	@PostMapping()
	public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userDTO) {
		userService.createUser(userDTO);
		return ResponseEntity.noContent().build();
	}

	

	@ApiOperation(value = "Update User by given User Id")
	@PostMapping("{id}")
	public ResponseEntity<?> updateUserById(@PathVariable("id") String id, @RequestBody UserRequestDTO userDTO) {
		userService.updateUser(id, userDTO);
		return ResponseEntity.noContent().build();
	}

	@ApiOperation(value = "Delete User by given User Id")
	@PostMapping("{id}/delete")
	public ResponseEntity<?> deleteUserById(@PathVariable("id") String id) {
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

}
