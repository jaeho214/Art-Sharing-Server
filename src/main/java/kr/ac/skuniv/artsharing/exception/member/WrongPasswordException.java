package kr.ac.skuniv.artsharing.exception.member;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class WrongPasswordException extends BusinessLogicException {
    public WrongPasswordException() {
        super(ErrorCodeType.WRONG_PASSWORD);
    }
}
