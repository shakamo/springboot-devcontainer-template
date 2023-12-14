package backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication(scanBasePackages = {
    "backend.application",
    "backend.config",
    "backend.domain",
    "backend.infrastructure",
    "backend.presentation"
})
@EnableAsync
public class DashboardApplication {
  public static void main(String[] args) {
    SpringApplication.run(DashboardApplication.class, args);
  }
}