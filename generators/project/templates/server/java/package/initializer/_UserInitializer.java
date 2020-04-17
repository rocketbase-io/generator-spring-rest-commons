package <%= packageName %>.initializer;

import io.rocketbase.commons.model.AppUserEntity;
import io.rocketbase.commons.service.user.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Slf4j
@Service
public class UserInitializer implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private AppUserService appUserService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        AppUserEntity adminUser = appUserService.getByUsername("admin");
        if (adminUser == null) {
            appUserService.initializeUser("admin", "admin", "admin@localhost", true);
            log.info("initialized adminUser: admin/admin");
        }
        AppUserEntity user = appUserService.getByUsername("user");
        if (user == null) {
            appUserService.initializeUser("user", "user", "user@localhost", false);
            log.info("initialized adminUser: user/user");
        }
    }
}
