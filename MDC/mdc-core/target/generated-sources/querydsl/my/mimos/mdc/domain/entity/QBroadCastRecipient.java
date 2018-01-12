package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBroadCastRecipient is a Querydsl query type for BroadCastRecipient
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBroadCastRecipient extends EntityPathBase<BroadCastRecipient> {

    private static final long serialVersionUID = -89273994L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBroadCastRecipient broadCastRecipient = new QBroadCastRecipient("broadCastRecipient");

    public final QBroadCast broadCast;

    public final QGroup group;

    public final DateTimePath<java.util.Date> lastActivityDate = createDateTime("lastActivityDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> readDate = createDateTime("readDate", java.util.Date.class);

    public final StringPath readStatus = createString("readStatus");

    public final DateTimePath<java.util.Date> receivedDate = createDateTime("receivedDate", java.util.Date.class);

    public final NumberPath<Long> recepientId = createNumber("recepientId", Long.class);

    public final StringPath recipientType = createString("recipientType");

    public final QUser user;

    public QBroadCastRecipient(String variable) {
        this(BroadCastRecipient.class, forVariable(variable), INITS);
    }

    public QBroadCastRecipient(Path<? extends BroadCastRecipient> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBroadCastRecipient(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBroadCastRecipient(PathMetadata metadata, PathInits inits) {
        this(BroadCastRecipient.class, metadata, inits);
    }

    public QBroadCastRecipient(Class<? extends BroadCastRecipient> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.broadCast = inits.isInitialized("broadCast") ? new QBroadCast(forProperty("broadCast"), inits.get("broadCast")) : null;
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group"), inits.get("group")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

