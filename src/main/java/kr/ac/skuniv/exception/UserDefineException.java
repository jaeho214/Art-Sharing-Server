package kr.ac.skuniv.exception;

import lombok.Getter;

@Getter
public class UserDefineException extends RuntimeException {

	//예외처리
	public UserDefineException(String message) {
		super(message);
	}

}
