package <%= packageName %>.config;

import io.rocketbase.commons.security.CommonsPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Optional;

@Configuration
@EnableMongoAuditing
public class MongoDbConfig {

    @Bean
    public AuditorAware<String> auditProvider() {
        return new SpringSecurityAuditorAware();
    }

    public static class SpringSecurityAuditorAware implements AuditorAware<String> {

        @Override
        public Optional<String> getCurrentAuditor() {
            CommonsPrincipal current = CommonsPrincipal.getCurrent();
            if (current != null) {
                return Optional.of(current.getUsername());
            }
            return Optional.empty();
        }
    }


}
