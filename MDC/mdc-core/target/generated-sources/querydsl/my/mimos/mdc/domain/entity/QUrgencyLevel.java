package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUrgencyLevel is a Querydsl query type for UrgencyLevel
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUrgencyLevel extends EntityPathBase<UrgencyLevel> {

    private static final long serialVersionUID = 1704405881L;

    public static final QUrgencyLevel urgencyLevel1 = new QUrgencyLevel("urgencyLevel1");

    public final QEntityBase _super = new QEntityBase(this);

    public final DateTimePath<java.util.Date> createdDate = createDateTime("createdDate", java.util.Date.class);

    public final DateTimePath<java.util.Date> modifiedDate = createDateTime("modifiedDate", java.util.Date.class);

    public final StringPath priority = createString("priority");

    public final StringPath urgencyLevel = createString("urgencyLevel");

    public final NumberPath<Long> urgencyLevelId = createNumber("urgencyLevelId", Long.class);

    public QUrgencyLevel(String variable) {
        super(UrgencyLevel.class, forVariable(variable));
    }

    public QUrgencyLevel(Path<? extends UrgencyLevel> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUrgencyLevel(PathMetadata metadata) {
        super(UrgencyLevel.class, metadata);
    }

}

