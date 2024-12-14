package com.moma.start_school_demo.product;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
interface ProductRepository extends JpaRepository<Product, Long> {
}
