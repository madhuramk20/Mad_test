package my.com.api.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {

	private String errorMessage;
}
