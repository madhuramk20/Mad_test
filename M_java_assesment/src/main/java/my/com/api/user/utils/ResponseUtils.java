package my.com.api.user.utils;

import org.springframework.http.ResponseEntity;

public class ResponseUtils {

	public static ResponseEntity<?> emptyOkResponse() {
		return ResponseEntity.noContent().build();
	}
}
