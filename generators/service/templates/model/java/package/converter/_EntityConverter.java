package <%= packageName %>.converter;

import io.rocketbase.commons.converter.EntityReadWriteConverter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
import <%= packageName %>.model.<%= entityName %>Entity;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralConfig.class)
public interface <%= entityName %>Converter extends EntityReadWriteConverter<<%= entityName %>Entity, <%= entityName %>Read, <%= entityName %>Write> {

    <%= entityName %>Read fromEntity(<%= entityName %>Entity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    <%= entityName %>Entity newEntity(<%= entityName %>Write workspace);

    @InheritConfiguration()
    <%= entityName %>Entity updateEntityFromEdit(<%= entityName %>Write write, @MappingTarget <%= entityName %>Entity entity);
}
