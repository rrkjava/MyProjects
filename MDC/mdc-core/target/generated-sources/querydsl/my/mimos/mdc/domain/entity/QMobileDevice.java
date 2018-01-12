package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMobileDevice is a Querydsl query type for MobileDevice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMobileDevice extends EntityPathBase<MobileDevice> {

    private static final long serialVersionUID = 905938774L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMobileDevice mobileDevice = new QMobileDevice("mobileDevice");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final NumberPath<Long> deviceId = createNumber("deviceId", Long.class);

    public final StringPath deviceStatus = createString("deviceStatus");

    public final StringPath deviceToken = createString("deviceToken");

    public final StringPath deviceType = createString("deviceType");

    public final DateTimePath<java.util.Date> updatedDate = createDateTime("updatedDate", java.util.Date.class);

    public final QUser user;

    public QMobileDevice(String variable) {
        this(MobileDevice.class, forVariable(variable), INITS);
    }

    public QMobileDevice(Path<? extends MobileDevice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMobileDevice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMobileDevice(PathMetadata metadata, PathInits inits) {
        this(MobileDevice.class, metadata, inits);
    }

    public QMobileDevice(Class<? extends MobileDevice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

