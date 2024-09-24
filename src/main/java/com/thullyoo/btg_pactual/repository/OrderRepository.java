package com.thullyoo.btg_pactual.repository;

import com.thullyoo.btg_pactual.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long> {

    Page<Order> findByClientId(Long clientId, PageRequest request);
}
