package kr.ac.skuniv.artsharing.domain.entity.member;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1634704829L;

    public static final QMember member = new QMember("member1");

    public final kr.ac.skuniv.artsharing.util.QJpaBasePersistable _super = new kr.ac.skuniv.artsharing.util.QJpaBasePersistable(this);

    public final StringPath affiliation = createString("affiliation");

    public final StringPath age = createString("age");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedAt = _super.lastModifiedAt;

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final EnumPath<kr.ac.skuniv.artsharing.domain.roles.MemberRole> role = createEnum("role", kr.ac.skuniv.artsharing.domain.roles.MemberRole.class);

    public final StringPath sex = createString("sex");

    public final StringPath userId = createString("userId");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

