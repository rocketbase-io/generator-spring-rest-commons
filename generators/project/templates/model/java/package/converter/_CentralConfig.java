package <%= packageName %>.converter;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
        unmappedTargetPolicy = ReportingPolicy.WARN,
        componentModel = "spring",
        builder = @Builder(disableBuilder = true)
)
public interface CentralConfig {
}
