package hu.webuni.orderservice;

import hu.webuni.orderservice.service.InitDbService;
import hu.thesis.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {OrderServiceApplication.class, JwtAuthFilter.class})
@RequiredArgsConstructor
public class OrderServiceApplication implements CommandLineRunner {

    private final InitDbService initDbService;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initDbService.deleteDb();
        initDbService.addInitData();
    }
}
