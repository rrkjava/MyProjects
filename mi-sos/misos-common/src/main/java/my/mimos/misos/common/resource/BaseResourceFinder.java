/**
 * 
 */
package my.mimos.misos.common.resource;

import java.io.Serializable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author nandika.liyanage
 *
 */
public interface BaseResourceFinder<R extends AbstractResource, ID extends Serializable> {
	Page<R> findAll(Pageable pageable);
	
	R findOne(ID id);
}
