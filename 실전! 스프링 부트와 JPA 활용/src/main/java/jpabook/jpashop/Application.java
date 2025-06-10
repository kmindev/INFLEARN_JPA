package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public Hibernate6Module hibernate6Module() {
//        Hibernate6Module hibernate6Module = new Hibernate6Module();
////        hibernate6Module.configure(Hibernate6Module.Feature.FORCE_LAZY_LOADING, true);
//        return hibernate6Module;
//    }

}
