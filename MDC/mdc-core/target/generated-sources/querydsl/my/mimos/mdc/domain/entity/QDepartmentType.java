package my.mimos.mdc.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDepartmentType is a Querydsl query type for DepartmentType
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDepartmentType extends EntityPathBase<DepartmentType> {

    private static final long serialVersionUID = 916145130L;

    public static final QDepartmentType departmentType = new QDepartmentType("departmentType");

    public final QEntityBase _super = new QEntityBase(this);

    public final StringPath deptTypeDesc = createString("deptTypeDesc");

    public final NumberPath<Long> deptTypeId = createNumber("deptTypeId", Long.class);

    public final StringPath deptTypeName = createString("deptTypeName");

    public QDepartmentType(String variable) {
        super(DepartmentType.class, forVariable(variable));
    }

    public QDepartmentType(Path<? extends DepartmentType> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartmentType(PathMetadata metadata) {
        super(DepartmentType.class, metadata);
    }

}

