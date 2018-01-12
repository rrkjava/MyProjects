package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRecipient is a Querydsl query type for Recipient
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRecipient extends EntityPathBase<Recipient> {

    private static final long serialVersionUID = 127470875L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRecipient recipient1 = new QRecipient("recipient1");

    public final QEntityBase _super = new QEntityBase(this);

    public final NumberPath<Long> ack_id = createNumber("ack_id", Long.class);

    public final DateTimePath<java.util.Date> assignedDate = createDateTime("assignedDate", java.util.Date.class);

    public final QDepartment dept;

    public final QGroup group;

    public final DateTimePath<java.util.Date> lastActivityDate = createDateTime("lastActivityDate", java.util.Date.class);

    public final QQuery query;

    public final StringPath readStatus = createString("readStatus");

    public final NumberPath<Long> recepientId = createNumber("recepientId", Long.class);

    public final QUser recipient;

    public final StringPath recipientType = createString("recipientType");

    public QRecipient(String variable) {
        this(Recipient.class, forVariable(variable), INITS);
    }

    public QRecipient(Path<? extends Recipient> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRecipient(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRecipient(PathMetadata metadata, PathInits inits) {
        this(Recipient.class, metadata, inits);
    }

    public QRecipient(Class<? extends Recipient> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.dept = inits.isInitialized("dept") ? new QDepartment(forProperty("dept"), inits.get("dept")) : null;
        this.group = inits.isInitialized("group") ? new QGroup(forProperty("group"), inits.get("group")) : null;
        this.query = inits.isInitialized("query") ? new QQuery(forProperty("query"), inits.get("query")) : null;
        this.recipient = inits.isInitialized("recipient") ? new QUser(forProperty("recipient"), inits.get("recipient")) : null;
    }

}

