package kr.ac.skuniv.artsharing.exception.reply;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class ReplyNotFoundException extends BusinessLogicException {
    public ReplyNotFoundException() {
        super(ErrorCodeType.REPLY_NOT_FOUND);
    }
}
