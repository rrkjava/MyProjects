package my.mimos.mdc.domain.mapper;

import org.springframework.stereotype.Component;

import lombok.Getter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.ConfigurableMapper;

/**
 * 
 * @author beaula.fernandez
 *
 */

@Component
public abstract class MapperBase extends ConfigurableMapper {

	@Getter
	protected MapperFacade mapperFacade;
}
