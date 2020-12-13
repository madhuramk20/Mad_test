package my.com.api.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRequestDTO extends BaseUserDTO {

	@ApiModelProperty(required = true)
	private String firstName;
	
	@ApiModelProperty(required = true)
	private String id;
}
