package com.training.sportbetting;

import com.training.sportbetting.repository.jpa.SportEventRepository;
import com.training.sportbetting.repository.jpa.UserRepository;
import com.training.sportbetting.service.SportEventService;
import com.training.sportbetting.service.WagerService;
import com.training.sportbetting.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * Class creates dependencies and contains application entry point.
 */
@Component
public final class App {

    private static final int PORT = 8080;

    private SportEventRepository inMemorySportEventRepository;
    private UserRepository userRepository;

    private SportEventService sportEventService;
    private WagerService wagerService;

    @Autowired
    public App(SportEventRepository inMemorySportEventRepository,
               SportEventService sportEventService,
               WagerService wagerService, UserRepository userRepository) {
        this.inMemorySportEventRepository = inMemorySportEventRepository;
        this.sportEventService = sportEventService;
        this.wagerService = wagerService;
        this.userRepository = userRepository;
    }

    /**
     * Application entry point.
     *
     * @param args cmd args.
     */
    public static void main(String[] args) throws Exception {
        var context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocation("com.training.sportbetting.configuration");

        new EmbeddedJetty(context, PORT, "default");
    }

//    @EventListener(ContextRefreshedEvent.class)
    public void appStart() {
        DataGenerator.generateData(inMemorySportEventRepository, userRepository, wagerService, sportEventService);
    }
}
