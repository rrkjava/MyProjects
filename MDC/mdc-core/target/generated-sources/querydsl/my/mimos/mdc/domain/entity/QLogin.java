package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLogin is a Querydsl query type for Login
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLogin extends EntityPathBase<Login> {

    private static final long serialVersionUID = -346596501L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLogin login = new QLogin("login");

    public final QMobileDevice deviceId;

    public final DateTimePath<java.util.Date> loginDate = createDateTime("loginDate", java.util.Date.class);

    public final NumberPath<Long> loginId = createNumber("loginId", Long.class);

    public final DateTimePath<java.util.Date> logoutDate = createDateTime("logoutDate", java.util.Date.class);

    public final StringPath status = createString("status");

    public final StringPath username = createString("username");

    public QLogin(String variable) {
        this(Login.class, forVariable(variable), INITS);
    }

    public QLogin(Path<? extends Login> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLogin(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLogin(PathMetadata metadata, PathInits inits) {
        this(Login.class, metadata, inits);
    }

    public QLogin(Class<? extends Login> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deviceId = inits.isInitialized("deviceId") ? new QMobileDevice(forProperty("deviceId"), inits.get("deviceId")) : null;
    }

}

