package <%= packageName %>.initializer;

import io.rocketbase.commons.model.AppUser;
import io.rocketbase.commons.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Service
public class UserInitializer {

    @Resource
    private AppUserService appUserService;

    @PostConstruct
    public void postConstruct() {
        AppUser adminUser = appUserService.getByUsername("admin");
        if (adminUser == null) {
            appUserService.initializeUser("admin", "admin", "admin@localhost", true);
            log.info("initialized adminUser: admin/admin");
        }
        AppUser user = appUserService.getByUsername("user");
        if (user == null) {
            appUserService.initializeUser("user", "user", "user@localhost", false);
            log.info("initialized adminUser: user/user");
        }
    }
}
