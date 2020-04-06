package kr.ac.skuniv.artsharing.exception.artImage;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class ArtImageNotFoundException extends BusinessLogicException {
    public ArtImageNotFoundException() {
        super(ErrorCodeType.IMAGE_NOT_FOUND);
    }
}
