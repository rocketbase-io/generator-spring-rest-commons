package <%= packageName %>.config;

import io.rocketbase.commons.security.CommonsPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
<%_ if (mongoDb) { _%>
import org.springframework.data.mongodb.config.EnableMongoAuditing;
<%_ } else { _%>
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
<%_ } _%>

import java.util.Optional;

@Configuration
<%_ if (mongoDb) { _%>
@EnableMongoAuditing
<%_ } else { _%>
@EnableJpaAuditing
<%_ } _%>
public class AuditDbConfig {

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
