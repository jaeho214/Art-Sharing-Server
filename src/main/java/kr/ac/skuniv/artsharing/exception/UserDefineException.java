package kr.ac.skuniv.artsharing.exception;

import lombok.Getter;

@Getter
public class UserDefineException extends RuntimeException {

	private String originalErrorMessage;
	private String errorMethod;
	//예외처리
	public UserDefineException(String message) {
		super(message);
	}

	public UserDefineException(String message, String originalErrorMessage){
		super(message);
		this.originalErrorMessage = originalErrorMessage;
	}

}
