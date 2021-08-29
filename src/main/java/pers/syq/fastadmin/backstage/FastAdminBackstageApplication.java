package pers.syq.fastadmin.backstage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"pers.syq.fastadmin.backstage","cn.hutool.extra.spring"})
public class FastAdminBackstageApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastAdminBackstageApplication.class, args);
    }

}
