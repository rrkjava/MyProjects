package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBroadCast is a Querydsl query type for BroadCast
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBroadCast extends EntityPathBase<BroadCast> {

    private static final long serialVersionUID = 1982527459L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBroadCast broadCast = new QBroadCast("broadCast");

    public final QEntityBase _super = new QEntityBase(this);

    public final ListPath<Attachment, QAttachment> attachment = this.<Attachment, QAttachment>createList("attachment", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final QUser broadcastBy;

    public final DateTimePath<java.util.Date> broadcastDate = createDateTime("broadcastDate", java.util.Date.class);

    public final StringPath message = createString("message");

    public final NumberPath<Long> messageId = createNumber("messageId", Long.class);

    public final StringPath subject = createString("subject");

    public QBroadCast(String variable) {
        this(BroadCast.class, forVariable(variable), INITS);
    }

    public QBroadCast(Path<? extends BroadCast> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBroadCast(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBroadCast(PathMetadata metadata, PathInits inits) {
        this(BroadCast.class, metadata, inits);
    }

    public QBroadCast(Class<? extends BroadCast> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.broadcastBy = inits.isInitialized("broadcastBy") ? new QUser(forProperty("broadcastBy"), inits.get("broadcastBy")) : null;
    }

}

