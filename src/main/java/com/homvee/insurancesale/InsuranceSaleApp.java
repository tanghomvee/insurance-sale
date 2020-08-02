package com.homvee.insurancesale;

import com.homvee.insurancesale.web.interceptors.UsrAuthInterceptor;
import com.homvee.insurancesale.web.interceptors.UsrLoginInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.homvee.insurancesale.dao"})
@EntityScan(basePackages = {"com.homvee.insurancesale.dao.entities"})
@ComponentScan(basePackages = {"com.homvee"})
public class InsuranceSaleApp implements WebMvcConfigurer {

    @Resource
    private UsrLoginInterceptor usrLoginInterceptor;
    @Resource
    private UsrAuthInterceptor usrAuthInterceptor;
    public static void main(String[] args) {
        SpringApplication.run(InsuranceSaleApp.class, args);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(usrLoginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/saleman/login/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/")
        ;
       /* registry.addInterceptor(usrAuthInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/saleman/login/**")
                .excludePathPatterns("/static/**")
                .excludePathPatterns("/index.html")
                .excludePathPatterns("/")*/
        ;

    }

}
