/**
 * 
 */
package my.mimos.misos.domain.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */

@NoRepositoryBean
public interface ReadOnlyRepository <T, ID extends Serializable> extends Repository<T, ID> {

	T findOne(ID id);

    List<T> findAll();
}
