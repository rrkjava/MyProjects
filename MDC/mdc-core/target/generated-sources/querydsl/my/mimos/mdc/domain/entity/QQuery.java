package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuery is a Querydsl query type for Query
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQuery extends EntityPathBase<Query> {

    private static final long serialVersionUID = -341801782L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuery query = new QQuery("query");

    public final QEntityBase _super = new QEntityBase(this);

    public final StringPath approvalComment = createString("approvalComment");

    public final QUser approvedBy;

    public final DateTimePath<java.util.Date> approvedDate = createDateTime("approvedDate", java.util.Date.class);

    public final ListPath<Attachment, QAttachment> attachment = this.<Attachment, QAttachment>createList("attachment", Attachment.class, QAttachment.class, PathInits.DIRECT2);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final StringPath description = createString("description");

    public final BooleanPath forwardFlag = createBoolean("forwardFlag");

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final NumberPath<Long> queryId = createNumber("queryId", Long.class);

    public final StringPath queryStatus = createString("queryStatus");

    public final BooleanPath readReciept = createBoolean("readReciept");

    public final SetPath<Recipient, QRecipient> recipients = this.<Recipient, QRecipient>createSet("recipients", Recipient.class, QRecipient.class, PathInits.DIRECT2);

    public final BooleanPath senderReadStatus = createBoolean("senderReadStatus");

    public final QUser sentBy;

    public final StringPath subject = createString("subject");

    public final QUrgencyLevel urgencyLevel;

    public QQuery(String variable) {
        this(Query.class, forVariable(variable), INITS);
    }

    public QQuery(Path<? extends Query> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuery(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuery(PathMetadata metadata, PathInits inits) {
        this(Query.class, metadata, inits);
    }

    public QQuery(Class<? extends Query> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approvedBy = inits.isInitialized("approvedBy") ? new QUser(forProperty("approvedBy"), inits.get("approvedBy")) : null;
        this.sentBy = inits.isInitialized("sentBy") ? new QUser(forProperty("sentBy"), inits.get("sentBy")) : null;
        this.urgencyLevel = inits.isInitialized("urgencyLevel") ? new QUrgencyLevel(forProperty("urgencyLevel")) : null;
    }

}

