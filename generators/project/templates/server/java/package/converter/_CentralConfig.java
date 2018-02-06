package <%= packageName %>.converter;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        componentModel = "spring"
)
public interface CentralConfig {
}
