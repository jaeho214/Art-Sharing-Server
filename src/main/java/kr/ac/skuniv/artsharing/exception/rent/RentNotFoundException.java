package kr.ac.skuniv.artsharing.exception.rent;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class RentNotFoundException extends BusinessLogicException {
    public RentNotFoundException() {
        super(ErrorCodeType.RENT_NOT_FOUND);
    }
}
