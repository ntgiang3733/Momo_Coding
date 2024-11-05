package com.momo.configuration;

import com.momo.entity.Product;
import com.momo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfig {

    @Bean
    public CommandLineRunner initData(ProductRepository productRepository) {
        return args -> {
            productRepository.save(new Product("Coke", 10000L, "https://product.hstatic.net/1000141988/product/nuoc_ngot_cocacola_vi_nguyen_ban_320_ml_5545f89b5d434c548a8bff6118a3ed49.jpg"));
            productRepository.save(new Product("Pepsi", 10000L, "https://product.hstatic.net/200000407109/product/nuoc-ngot-pepsi-cola-330ml-202008131510316142_8307f014cca54f87883652601bca9600.jpg"));
            productRepository.save(new Product("Soda", 20000L, "https://micaynagathuduc.com/wp-content/uploads/2022/07/soda-blue-5884.png"));
        };
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
