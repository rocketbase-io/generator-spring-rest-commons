package <%= packageName %>.converter;

import io.rocketbase.commons.converter.EntityReadWriteConverter;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Read;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityName %>Write;
import <%= packageName %>.model.<%= entityName %>;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralConfig.class)
public interface <%= entityName %>Converter extends EntityReadWriteConverter<<%= entityName %>, <%= entityName %>Read, <%= entityName %>Write> {

    <%= entityName %> toEntity(<%= entityName %>Read read);

    @InheritInverseConfiguration
    <%= entityName %>Read fromEntity(<%= entityName %> entity);

    List<<%= entityName %>Read> fromEntities(List<<%= entityName %>> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    <%= entityName %> newEntity(<%= entityName %>Write workspace);

    @InheritConfiguration()
    <%= entityName %> updateEntityFromEdit(<%= entityName %>Write write, @MappingTarget <%= entityName %> entity);
}
