package com.example.repository;

import com.example.domain.Order;

public interface OrderRepository {

    Order save(Order order);
}
