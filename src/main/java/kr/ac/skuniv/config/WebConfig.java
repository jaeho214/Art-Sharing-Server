package kr.ac.skuniv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Import(SwaggerConfig.class)
public class WebConfig implements WebMvcConfigurer {
    //FIXME : CORS 관련 이슈를 위해 만들어주었는데 과연 필요한가?
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**/**")
                .allowedOrigins("*")
                .allowedMethods("PUT", "DELETE", "GET", "POST")
                .allowedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }

    @Override
    // 시큐리티를 사용해서 swagger를 적용하기 위해 추가한 부분
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/WEB-INF/resources/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Bean
    //Required request part 'file' is not present 에러 떠서 추가
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
