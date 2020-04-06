package kr.ac.skuniv.artsharing.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDefineException extends RuntimeException {

	private String originalErrorMessage;

	public UserDefineException(String message) {
		super(message);
	}

	@Builder
	public UserDefineException(String message, String originalErrorMessage){
		super(message);
		this.originalErrorMessage = originalErrorMessage;
	}

}
