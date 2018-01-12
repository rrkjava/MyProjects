package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAcknowledgment is a Querydsl query type for Acknowledgment
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAcknowledgment extends EntityPathBase<Acknowledgment> {

    private static final long serialVersionUID = -2089555451L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAcknowledgment acknowledgment = new QAcknowledgment("acknowledgment");

    public final QUser ackBy;

    public final StringPath ackComment = createString("ackComment");

    public final NumberPath<Long> ackId = createNumber("ackId", Long.class);

    public final StringPath ackStatus = createString("ackStatus");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final QQuery query;

    public final BooleanPath readReciept = createBoolean("readReciept");

    public QAcknowledgment(String variable) {
        this(Acknowledgment.class, forVariable(variable), INITS);
    }

    public QAcknowledgment(Path<? extends Acknowledgment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAcknowledgment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAcknowledgment(PathMetadata metadata, PathInits inits) {
        this(Acknowledgment.class, metadata, inits);
    }

    public QAcknowledgment(Class<? extends Acknowledgment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.ackBy = inits.isInitialized("ackBy") ? new QUser(forProperty("ackBy"), inits.get("ackBy")) : null;
        this.query = inits.isInitialized("query") ? new QQuery(forProperty("query"), inits.get("query")) : null;
    }

}

