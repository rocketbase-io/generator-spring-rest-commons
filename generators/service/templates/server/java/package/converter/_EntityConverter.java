package <%= packageName %>.converter;

import io.rocketbase.commons.converter.EntityDataEditConverter;
import <%= packageName %>.dto.data.<%= entityName %>Data;
import <%= packageName %>.dto.edit.<%= entityName %>Edit;
import <%= packageName %>.model.<%= entityName %>;
import org.mapstruct.*;

import java.util.List;

@Mapper(config = CentralConfig.class)
public interface <%= entityName %>Converter extends EntityDataEditConverter<<%= entityName %>, <%= entityName %>Data, <%= entityName %>Edit> {

    <%= entityName %> toEntity(<%= entityName %>Data data);

    @InheritInverseConfiguration
    <%= entityName %>Data fromEntity(<%= entityName %> entity);

    List<<%= entityName %>Data> fromEntities(List<<%= entityName %>> entities);

    @Mappings({
            @Mapping(target = "id", ignore = true),
    })
    <%= entityName %> newEntity(<%= entityName %>Edit workspace);

    @InheritConfiguration()
    void updateEntityFromEdit(<%= entityName %>Edit edit, @MappingTarget <%= entityName %> entity);
}
