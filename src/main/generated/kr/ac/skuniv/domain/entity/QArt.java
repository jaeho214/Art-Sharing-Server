package kr.ac.skuniv.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArt is a Querydsl query type for Art
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArt extends EntityPathBase<Art> {

    private static final long serialVersionUID = 300503895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArt art = new QArt("art");

    public final StringPath artName = createString("artName");

    public final StringPath explanation = createString("explanation");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isRent = createBoolean("isRent");

    public final QMember member;

    public final StringPath price = createString("price");

    public final DateTimePath<java.time.LocalDateTime> regDate = createDateTime("regDate", java.time.LocalDateTime.class);

    public final ListPath<Reply, QReply> replies = this.<Reply, QReply>createList("replies", Reply.class, QReply.class, PathInits.DIRECT2);

    public QArt(String variable) {
        this(Art.class, forVariable(variable), INITS);
    }

    public QArt(Path<? extends Art> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArt(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArt(PathMetadata metadata, PathInits inits) {
        this(Art.class, metadata, inits);
    }

    public QArt(Class<? extends Art> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

