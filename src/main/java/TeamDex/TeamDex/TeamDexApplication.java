package TeamDex.TeamDex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "entity")
@ComponentScan(basePackages = {"security", "config", "controllers", "service"})
@EnableJpaRepositories(basePackages = "repository")
public class TeamDexApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamDexApplication.class, args);
	}

}
