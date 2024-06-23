package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.OrderORM;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface OrderRepository extends CrudRepository<OrderORM, String> {

}
