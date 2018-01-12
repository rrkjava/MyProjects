/**
 * 
 */
package my.mimos.misos.mapper;

import lombok.Getter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.ConfigurableMapper;

/**
 * @author Shaiful Hisham Mat Jali
 *
 */
public abstract class MapperBase extends ConfigurableMapper {

	@Getter
	protected MapperFacade mapperFacade;
}
