package kr.ac.skuniv.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException {

	//예외처리
	public MemberException(String message) {
		super(message);
	}

}
