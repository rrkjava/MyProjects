package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QNotificationTracker is a Querydsl query type for NotificationTracker
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNotificationTracker extends EntityPathBase<NotificationTracker> {

    private static final long serialVersionUID = -1100654513L;

    public static final QNotificationTracker notificationTracker = new QNotificationTracker("notificationTracker");

    public final StringPath devices = createString("devices");

    public final StringPath message = createString("message");

    public final DateTimePath<java.util.Date> notificationDate = createDateTime("notificationDate", java.util.Date.class);

    public final StringPath queryId = createString("queryId");

    public final NumberPath<Long> trackerId = createNumber("trackerId", Long.class);

    public QNotificationTracker(String variable) {
        super(NotificationTracker.class, forVariable(variable));
    }

    public QNotificationTracker(Path<? extends NotificationTracker> path) {
        super(path.getType(), path.getMetadata());
    }

    public QNotificationTracker(PathMetadata metadata) {
        super(NotificationTracker.class, metadata);
    }

}

