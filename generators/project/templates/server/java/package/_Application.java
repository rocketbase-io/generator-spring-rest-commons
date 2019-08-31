package <%= packageName %>;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<%_ if (!mongoDb && auth) { _%>
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
<%_ } _%>

@SpringBootApplication
<%_ if (!mongoDb && auth) { _%>
@EnableJpaRepositories(basePackages = {"io.rocketbase.commons", "<%= packageName %>"})
@EntityScan({"io.rocketbase.commons", "<%= packageName %>"})
<%_ } _%>
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
