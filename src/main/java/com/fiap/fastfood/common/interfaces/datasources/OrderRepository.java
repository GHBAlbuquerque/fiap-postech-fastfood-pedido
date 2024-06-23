package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.OrderORM;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface OrderRepository extends CrudRepository<OrderORM, String> {
    List<OrderORM> findAll();

    List<OrderORM> findAllByOrderByCreatedAt();
}
