package TeamDex.TeamDex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "entity")
public class TeamDexApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamDexApplication.class, args);
	}

}
