package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQueryTracker is a Querydsl query type for QueryTracker
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QQueryTracker extends EntityPathBase<QueryTracker> {

    private static final long serialVersionUID = -154545394L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQueryTracker queryTracker = new QQueryTracker("queryTracker");

    public final NumberPath<Long> actionByDept = createNumber("actionByDept", Long.class);

    public final StringPath actionByRole = createString("actionByRole");

    public final QUser actionDoneBy;

    public final BooleanPath actionStatus = createBoolean("actionStatus");

    public final DateTimePath<java.util.Date> deadlineDate = createDateTime("deadlineDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> endDate = createDateTime("endDate", java.util.Date.class);

    public final QQuery query;

    public final StringPath queryAction = createString("queryAction");

    public final QResponse response;

    public final DateTimePath<java.util.Date> startDate = createDateTime("startDate", java.util.Date.class);

    public final NumberPath<Long> trackerId = createNumber("trackerId", Long.class);

    public final BooleanPath valid = createBoolean("valid");

    public QQueryTracker(String variable) {
        this(QueryTracker.class, forVariable(variable), INITS);
    }

    public QQueryTracker(Path<? extends QueryTracker> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQueryTracker(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQueryTracker(PathMetadata metadata, PathInits inits) {
        this(QueryTracker.class, metadata, inits);
    }

    public QQueryTracker(Class<? extends QueryTracker> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.actionDoneBy = inits.isInitialized("actionDoneBy") ? new QUser(forProperty("actionDoneBy"), inits.get("actionDoneBy")) : null;
        this.query = inits.isInitialized("query") ? new QQuery(forProperty("query"), inits.get("query")) : null;
        this.response = inits.isInitialized("response") ? new QResponse(forProperty("response"), inits.get("response")) : null;
    }

}

