package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1257834615L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final QEntityBase _super = new QEntityBase(this);

    public final StringPath activatedStatus = createString("activatedStatus");

    public final StringPath address = createString("address");

    public final StringPath approvalStatus = createString("approvalStatus");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final QDepartment department;

    public final StringPath emailId = createString("emailId");

    public final StringPath firstName = createString("firstName");

    public final StringPath lastName = createString("lastName");

    public final DateTimePath<java.util.Date> lastPasswordReset = createDateTime("lastPasswordReset", java.util.Date.class);

    public final StringPath password = createString("password");

    public final StringPath phone = createString("phone");

    public final SetPath<Role, QRole> role = this.<Role, QRole>createSet("role", Role.class, QRole.class, PathInits.DIRECT2);

    public final StringPath statusComment = createString("statusComment");

    public final DateTimePath<java.util.Date> updatedDate = createDateTime("updatedDate", java.util.Date.class);

    public final QAttachment upload;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartment(forProperty("department"), inits.get("department")) : null;
        this.upload = inits.isInitialized("upload") ? new QAttachment(forProperty("upload"), inits.get("upload")) : null;
    }

}

