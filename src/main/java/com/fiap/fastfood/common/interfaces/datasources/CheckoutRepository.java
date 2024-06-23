package com.fiap.fastfood.common.interfaces.datasources;

import com.fiap.fastfood.external.orm.CheckoutORM;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@EnableScan
public interface CheckoutRepository extends CrudRepository<CheckoutORM, String> {
    List<CheckoutORM> findAllByOrderByCreatedAtAsc();
}
