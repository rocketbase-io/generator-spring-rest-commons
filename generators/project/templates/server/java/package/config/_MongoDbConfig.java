package <%= packageName %>.config;

import io.rocketbase.commons.model.AppUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
            Authentication authentication = SecurityContextHolder.getContext()
                    .getAuthentication();

            if (!(authentication instanceof AppUser)) {
                return Optional.empty();
            }

            return Optional.of(((AppUser) authentication.getPrincipal()).getUsername());
        }
    }


}
