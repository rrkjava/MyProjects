package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachment is a Querydsl query type for Attachment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachment extends EntityPathBase<Attachment> {

    private static final long serialVersionUID = -1959584159L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachment attachment = new QAttachment("attachment");

    public final QBroadCast broadCast;

    public final QBroadCastResponse broadCastResponse;

    public final ArrayPath<byte[], Byte> content = createArray("content", byte[].class);

    public final StringPath contentType = createString("contentType");

    public final QQuery query;

    public final QResponse response;

    public final QUser uploadBy;

    public final DateTimePath<java.util.Date> uploadDate = createDateTime("uploadDate", java.util.Date.class);

    public final NumberPath<Long> uploadId = createNumber("uploadId", Long.class);

    public final NumberPath<Long> uploadSize = createNumber("uploadSize", Long.class);

    public final StringPath uploadTitle = createString("uploadTitle");

    public QAttachment(String variable) {
        this(Attachment.class, forVariable(variable), INITS);
    }

    public QAttachment(Path<? extends Attachment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachment(PathMetadata metadata, PathInits inits) {
        this(Attachment.class, metadata, inits);
    }

    public QAttachment(Class<? extends Attachment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.broadCast = inits.isInitialized("broadCast") ? new QBroadCast(forProperty("broadCast"), inits.get("broadCast")) : null;
        this.broadCastResponse = inits.isInitialized("broadCastResponse") ? new QBroadCastResponse(forProperty("broadCastResponse"), inits.get("broadCastResponse")) : null;
        this.query = inits.isInitialized("query") ? new QQuery(forProperty("query"), inits.get("query")) : null;
        this.response = inits.isInitialized("response") ? new QResponse(forProperty("response"), inits.get("response")) : null;
        this.uploadBy = inits.isInitialized("uploadBy") ? new QUser(forProperty("uploadBy"), inits.get("uploadBy")) : null;
    }

}

