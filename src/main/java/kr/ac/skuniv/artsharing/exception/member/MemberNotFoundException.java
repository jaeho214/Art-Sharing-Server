package kr.ac.skuniv.artsharing.exception.member;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class MemberNotFoundException extends BusinessLogicException {
    public MemberNotFoundException() {
        super(ErrorCodeType.MEMBER_NOT_FOUND);
    }
}
