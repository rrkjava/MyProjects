package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBroadCastResponse is a Querydsl query type for BroadCastResponse
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBroadCastResponse extends EntityPathBase<BroadCastResponse> {

    private static final long serialVersionUID = 461626692L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBroadCastResponse broadCastResponse = new QBroadCastResponse("broadCastResponse");

    public final QEntityBase _super = new QEntityBase(this);

    public final ListPath<Attachment, QAttachment> attachment = this.<Attachment, QAttachment>createList("attachment", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final QBroadCast broadCast;

    public final BooleanPath directReplyFlag = createBoolean("directReplyFlag");

    public final QUser responseBy;

    public final DateTimePath<java.util.Date> responseDate = createDateTime("responseDate", java.util.Date.class);

    public final NumberPath<Long> responseId = createNumber("responseId", Long.class);

    public final StringPath responseMessage = createString("responseMessage");

    public QBroadCastResponse(String variable) {
        this(BroadCastResponse.class, forVariable(variable), INITS);
    }

    public QBroadCastResponse(Path<? extends BroadCastResponse> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBroadCastResponse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBroadCastResponse(PathMetadata metadata, PathInits inits) {
        this(BroadCastResponse.class, metadata, inits);
    }

    public QBroadCastResponse(Class<? extends BroadCastResponse> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.broadCast = inits.isInitialized("broadCast") ? new QBroadCast(forProperty("broadCast"), inits.get("broadCast")) : null;
        this.responseBy = inits.isInitialized("responseBy") ? new QUser(forProperty("responseBy"), inits.get("responseBy")) : null;
    }

}

