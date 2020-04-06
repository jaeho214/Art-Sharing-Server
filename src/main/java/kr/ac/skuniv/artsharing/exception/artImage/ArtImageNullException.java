package kr.ac.skuniv.artsharing.exception.artImage;

import kr.ac.skuniv.artsharing.exception.BusinessLogicException;
import kr.ac.skuniv.artsharing.exception.ErrorCodeType;

public class ArtImageNullException extends BusinessLogicException {
    public ArtImageNullException() {
        super(ErrorCodeType.IMAGE_CANNOT_NULL);
    }
}
