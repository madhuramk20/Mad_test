package my.com.api.user.services;

import java.util.List;

import my.com.api.user.dto.UserRequestDTO;
import my.com.api.user.dto.UserResponseDTO;

public interface UserService {

	public List<UserResponseDTO> getAllUsers();

	public UserResponseDTO getUserById(String id);

	public void createUser(UserRequestDTO userDTO);

	public void updateUser(String id, UserRequestDTO userDTO);
	
	public void deleteUser(String id);
	
	
}
