package kr.ac.skuniv.artsharing.domain.entity;

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

    private static final long serialVersionUID = -690745852L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRent rent = new QRent("rent");

    public final QArt art;

    public final QMember member;

    public final StringPath price = createString("price");

    public final DatePath<java.time.LocalDate> rentDate = createDate("rentDate", java.time.LocalDate.class);

    public final NumberPath<Long> rentNo = createNumber("rentNo", Long.class);

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
        this.art = inits.isInitialized("art") ? new QArt(forProperty("art"), inits.get("art")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

