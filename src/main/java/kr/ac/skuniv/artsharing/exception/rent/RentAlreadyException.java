package kr.ac.skuniv.artsharing.exception.rent;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class RentAlreadyException extends BusinessLogicException {
    public RentAlreadyException() {
        super(ErrorCodeType.ALREADY_RENT);
    }
}
