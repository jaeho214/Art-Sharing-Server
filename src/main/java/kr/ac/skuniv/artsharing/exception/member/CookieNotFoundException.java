package kr.ac.skuniv.artsharing.exception.member;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class CookieNotFoundException extends BusinessLogicException {
    public CookieNotFoundException() {
        super(ErrorCodeType.COOKIE_NOT_FOUND);
    }
}
