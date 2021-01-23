package com.example.controller;

import com.example.domain.Order;
import com.example.domain.User;
import com.example.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
@SessionAttributes("order")
@RequestMapping("/orders")
@ConfigurationProperties(prefix = "taco.orders") // 커스텀 구성 속성을 사용하기 위한 설정
public class OrderController {

    private final OrderRepository orderRepository;
    private int pageSize = 20;

    @GetMapping("/current")
    public String orderForm(@AuthenticationPrincipal User user,
                            @ModelAttribute Order order) {

        if (order.getDeliveryName() == null) {
            order.setDeliveryName(user.getFullName());
        }

        if (order.getDeliveryStreet() == null) {
            order.setDeliveryStreet(user.getStreet());
        }

        if (order.getDeliveryCity() == null) {
            order.setDeliveryCity(user.getCity());
        }

        if (order.getDeliveryState() == null) {
            order.setDeliveryCity(user.getCity());
        }

        if (order.getDeliveryZip() == null) {
            order.setDeliveryZip(user.getZip());
        }

        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order,
                               Errors errors,
                               SessionStatus sessionStatus,
                               @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return "orderForm";
        }

        order.setUser(user); // 해당 주문을 사용자와 연결

        orderRepository.save(order);
        sessionStatus.setComplete();

        return "redirect:/";
    }

    @GetMapping
    public String ordersForUser(@AuthenticationPrincipal User user, Model model) {
        Pageable pageable = PageRequest.of(0, pageSize); // 최근 n개의 데이터만 조회하고자 할 경우
        model.addAttribute("orders", orderRepository.findByUserOrderByPlacedAtDesc(user, pageable));
        
        return "orderList";
    }
}
