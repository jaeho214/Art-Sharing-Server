package kr.ac.skuniv.artsharing.domain.entity.art;

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

    private static final long serialVersionUID = -18007635L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArt art = new QArt("art");

    public final kr.ac.skuniv.artsharing.util.QJpaBasePersistable _super = new kr.ac.skuniv.artsharing.util.QJpaBasePersistable(this);

    public final kr.ac.skuniv.artsharing.domain.entity.artImage.QArtImage artImage;

    public final StringPath artName = createString("artName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath explanation = createString("explanation");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isRent = createBoolean("isRent");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final kr.ac.skuniv.artsharing.domain.entity.member.QMember member;

    public final StringPath price = createString("price");

    public final ListPath<kr.ac.skuniv.artsharing.domain.entity.reply.Reply, kr.ac.skuniv.artsharing.domain.entity.reply.QReply> replies = this.<kr.ac.skuniv.artsharing.domain.entity.reply.Reply, kr.ac.skuniv.artsharing.domain.entity.reply.QReply>createList("replies", kr.ac.skuniv.artsharing.domain.entity.reply.Reply.class, kr.ac.skuniv.artsharing.domain.entity.reply.QReply.class, PathInits.DIRECT2);

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
        this.artImage = inits.isInitialized("artImage") ? new kr.ac.skuniv.artsharing.domain.entity.artImage.QArtImage(forProperty("artImage"), inits.get("artImage")) : null;
        this.member = inits.isInitialized("member") ? new kr.ac.skuniv.artsharing.domain.entity.member.QMember(forProperty("member")) : null;
    }

}

