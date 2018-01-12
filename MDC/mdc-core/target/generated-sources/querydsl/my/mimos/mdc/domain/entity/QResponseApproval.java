package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QResponseApproval is a Querydsl query type for ResponseApproval
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QResponseApproval extends EntityPathBase<ResponseApproval> {

    private static final long serialVersionUID = 815361122L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QResponseApproval responseApproval = new QResponseApproval("responseApproval");

    public final QUser approvalBy;

    public final StringPath approvalComment = createString("approvalComment");

    public final NumberPath<Long> approvalId = createNumber("approvalId", Long.class);

    public final StringPath approvalStatus = createString("approvalStatus");

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final BooleanPath readReciept = createBoolean("readReciept");

    public final QResponse response;

    public QResponseApproval(String variable) {
        this(ResponseApproval.class, forVariable(variable), INITS);
    }

    public QResponseApproval(Path<? extends ResponseApproval> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QResponseApproval(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QResponseApproval(PathMetadata metadata, PathInits inits) {
        this(ResponseApproval.class, metadata, inits);
    }

    public QResponseApproval(Class<? extends ResponseApproval> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.approvalBy = inits.isInitialized("approvalBy") ? new QUser(forProperty("approvalBy"), inits.get("approvalBy")) : null;
        this.response = inits.isInitialized("response") ? new QResponse(forProperty("response"), inits.get("response")) : null;
    }

}

