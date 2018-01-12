package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResponse is a Querydsl query type for Response
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResponse extends EntityPathBase<Response> {

    private static final long serialVersionUID = 1576997119L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResponse response = new QResponse("response");

    public final QEntityBase _super = new QEntityBase(this);

    public final StringPath approvalComment = createString("approvalComment");

    public final DateTimePath<java.util.Date> approvalDate = createDateTime("approvalDate", java.util.Date.class);

    public final ListPath<ResponseApproval, QResponseApproval> approvals = this.<ResponseApproval, QResponseApproval>createList("approvals", ResponseApproval.class, QResponseApproval.class, PathInits.DIRECT2);

    public final QUser approvedBy;

    public final ListPath<Attachment, QAttachment> attachment = this.<Attachment, QAttachment>createList("attachment", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath description = createString("description");

    public final BooleanPath directReplyFlag = createBoolean("directReplyFlag");

    public final BooleanPath finalReplyFlag = createBoolean("finalReplyFlag");

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final QQuery query;

    public final BooleanPath readReciept = createBoolean("readReciept");

    public final QUser responseBy;

    public final NumberPath<Long> responseId = createNumber("responseId", Long.class);

    public final StringPath responseStatus = createString("responseStatus");

    public final BooleanPath sendFinalReplyFlag = createBoolean("sendFinalReplyFlag");

    public final BooleanPath sensitiveFlag = createBoolean("sensitiveFlag");

    public QResponse(String variable) {
        this(Response.class, forVariable(variable), INITS);
    }

    public QResponse(Path<? extends Response> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResponse(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResponse(PathMetadata metadata, PathInits inits) {
        this(Response.class, metadata, inits);
    }

    public QResponse(Class<? extends Response> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approvedBy = inits.isInitialized("approvedBy") ? new QUser(forProperty("approvedBy"), inits.get("approvedBy")) : null;
        this.query = inits.isInitialized("query") ? new QQuery(forProperty("query"), inits.get("query")) : null;
        this.responseBy = inits.isInitialized("responseBy") ? new QUser(forProperty("responseBy"), inits.get("responseBy")) : null;
    }

}

