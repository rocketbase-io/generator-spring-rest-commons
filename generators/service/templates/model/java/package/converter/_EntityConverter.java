package <%= packageName %>.converter;

import io.rocketbase.commons.converter.EntityReadWriteConverter;
<%_ if (isDto) { _%>
import <%= packageName %>.dto.<%= entityNameRead %>;
<%_ } else { _%>
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameRead %>;
import <%= packageName %>.dto.<%= entityFolder %>.<%= entityNameWrite %>;
<%_ } _%>
import <%= packageName %>.model.<%= entityName %>Entity;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralConfig.class)
public interface <%= entityName %>Converter extends EntityReadWriteConverter<<%= entityName %>Entity, <%= entityNameRead %>, <%= entityNameWrite %>> {

    <%= entityNameRead %> fromEntity(<%= entityName %>Entity entity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    <%= entityName %>Entity newEntity(<%= entityNameWrite %> write);

    @InheritConfiguration()
    <%= entityName %>Entity updateEntityFromEdit(<%= entityNameWrite %> write, @MappingTarget <%= entityName %>Entity entity);
}
