package kr.ac.skuniv.artsharing.exception.art;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class ArtNotFoundException extends BusinessLogicException {
    public ArtNotFoundException() {
        super(ErrorCodeType.ART_NOT_FOUND);
    }
}
