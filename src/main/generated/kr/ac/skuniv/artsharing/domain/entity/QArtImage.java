package kr.ac.skuniv.artsharing.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArtImage is a Querydsl query type for ArtImage
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArtImage extends EntityPathBase<ArtImage> {

    private static final long serialVersionUID = -467363325L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArtImage artImage = new QArtImage("artImage");

    public final QArt art;

    public final NumberPath<Long> artImageNo = createNumber("artImageNo", Long.class);

    public final StringPath imageName = createString("imageName");

    public final StringPath imagePath = createString("imagePath");

    public final NumberPath<Long> imageSize = createNumber("imageSize", Long.class);

    public final StringPath imageType = createString("imageType");

    public final StringPath imageUrl = createString("imageUrl");

    public QArtImage(String variable) {
        this(ArtImage.class, forVariable(variable), INITS);
    }

    public QArtImage(Path<? extends ArtImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArtImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArtImage(PathMetadata metadata, PathInits inits) {
        this(ArtImage.class, metadata, inits);
    }

    public QArtImage(Class<? extends ArtImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.art = inits.isInitialized("art") ? new QArt(forProperty("art"), inits.get("art")) : null;
    }

}

