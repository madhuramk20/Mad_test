package my.com.api.user.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import my.com.api.exceptions.InvalidRequestException;
import my.com.api.user.dto.UserRequestDTO;
import my.com.api.user.dto.UserResponseDTO;
import my.com.api.user.model.UserData;
import my.com.api.user.repositories.UserRepository;
import my.com.api.user.services.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	/*
	 * @Value("${app.secret-key}") private String secretKeyValue;
	 */

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<UserResponseDTO> getAllUsers() {
		Iterable<UserData> users = userRepository.findAll();

		List<UserResponseDTO> UserDTOList = new ArrayList<>();
		for (UserData User : users) {
			UserResponseDTO UserDTO = modelMapper.map(User, UserResponseDTO.class);
			UserDTOList.add(UserDTO);
		}

		return UserDTOList;
	}

	@Override
	public UserResponseDTO getUserById(String id) {
		UserData user = null;
		try {
			user = userRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("User with id = " + id + " not found"));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		UserResponseDTO UserDTO = modelMapper.map(user, UserResponseDTO.class);

		return UserDTO;
	}

	@Override
	public void createUser(UserRequestDTO UserDTO) {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			String json = objectMapper.writeValueAsString(UserDTO);
			System.out.println("ResultingJSONstring = " + json);
			
			//System.out.println("AES req body" + AesUtils.encrypt(json, secretKeyValue));
			
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Error prossing result ");
		}
		
		
		if (UserDTO == null) {
			throw new InvalidRequestException("Request body not found");
		}

		// mandatory field check
		if (StringUtils.isEmpty(UserDTO.getFirstName())) {
			throw new InvalidRequestException("User  firstname is required");
		}

		// mandatory field check
		if (UserDTO.getId() == null) {
			throw new InvalidRequestException("User id is required");
		}

		UserData User = modelMapper.map(UserDTO, UserData.class);
		userRepository.save(User);

	}

	@Override
	public void updateUser(String id, UserRequestDTO UserDTO) {
		if (id == null) {
			throw new InvalidRequestException("Id is required");
		}

		UserData user;
		try {
			user = userRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
			user.setFirstName(UserDTO.getFirstName());
			user.setId(UserDTO.getId());
			userRepository.save(user);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteUser(String id) {
		UserData user;
		try {
			user = userRepository.findById(id)
					.orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
			userRepository.delete(user);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
