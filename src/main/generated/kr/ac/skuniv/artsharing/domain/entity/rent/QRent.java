package kr.ac.skuniv.artsharing.domain.entity.rent;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRent is a Querydsl query type for Rent
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRent extends EntityPathBase<Rent> {

    private static final long serialVersionUID = -1013646045L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRent rent = new QRent("rent");

    public final kr.ac.skuniv.artsharing.util.QJpaBasePersistable _super = new kr.ac.skuniv.artsharing.util.QJpaBasePersistable(this);

    public final kr.ac.skuniv.artsharing.domain.entity.art.QArt art;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final kr.ac.skuniv.artsharing.domain.entity.member.QMember member;

    public final StringPath price = createString("price");

    public final DatePath<java.time.LocalDate> returnDate = createDate("returnDate", java.time.LocalDate.class);

    public QRent(String variable) {
        this(Rent.class, forVariable(variable), INITS);
    }

    public QRent(Path<? extends Rent> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRent(PathMetadata metadata, PathInits inits) {
        this(Rent.class, metadata, inits);
    }

    public QRent(Class<? extends Rent> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.art = inits.isInitialized("art") ? new kr.ac.skuniv.artsharing.domain.entity.art.QArt(forProperty("art"), inits.get("art")) : null;
        this.member = inits.isInitialized("member") ? new kr.ac.skuniv.artsharing.domain.entity.member.QMember(forProperty("member")) : null;
    }

}

