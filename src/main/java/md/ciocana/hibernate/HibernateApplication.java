package md.ciocana.hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages =
		{"md.ciocana.hibernate",
				"md.ciocana.hibernate.repository",
				"md.ciocana.hibernate.entity",
				"md.ciocana.hibernate.controller"})
@Configuration
@EnableAspectJAutoProxy
@EnableJpaRepositories
public class HibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateApplication.class, args);
	}

}
