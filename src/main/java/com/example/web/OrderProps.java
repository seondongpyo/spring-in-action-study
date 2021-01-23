package com.example.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "taco.orders")
@Component
public class OrderProps {

    private int pageSize = 20;
}
