package <%= packageName %>;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
<%_ if (auth) { _%>
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
<%_ } _%>

@ComponentScan(basePackages = {"io.rocketbase.commons", "<%= packageName %>"})
<%_ if (auth) { _%>
@EnableMongoRepositories(basePackages = {"io.rocketbase.commons", "<%= packageName %>"})
<%_ } _%>
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
