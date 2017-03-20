package com.highgag.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.highgag.web.exception.HighgagException;
import com.highgag.web.response.SimpleFieldError;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.validation.FieldError;
import org.springframework.web.context.request.RequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "com.highgag.core.entity", basePackageClasses = {Jsr310JpaConverters.class})
@EnableJpaRepositories(basePackages = "com.highgag.core.repository")
public class HighgagWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(HighgagWebApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .registerModules(new JavaTimeModule());
        return objectMapper;
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @SuppressWarnings("unchecked")
            @Override
            public Map<String, Object> getErrorAttributes(RequestAttributes requestAttributes, boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(requestAttributes, false);
                Throwable throwable = getError(requestAttributes);

                if (throwable instanceof HighgagException) {
                    HighgagException e = (HighgagException) throwable;
                    errorAttributes.put("errors", e.getErrors());
                    errorAttributes.put("status", e.getStatusCode());
                    errorAttributes.put("message", e.getMessage());
                } else {
                    if (errorAttributes.containsKey("errors")) {
                        List<FieldError> fieldErrors = (List<FieldError>) errorAttributes.get("errors");
                        errorAttributes.put("errors", fieldErrors.stream()
                                .map(i -> new SimpleFieldError(i.getField(), i.getDefaultMessage()))
                                .collect(Collectors.toList()));
                        errorAttributes.put("message", "잘못된 요청입니다.");
                    }
                }

                return errorAttributes;
            }

        };
    }

}
