package com.works.repositories;

import com.works.entities.ProductStock;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductStockRepository extends JpaRepository<ProductStock,Integer> {
    List<ProductStock> findByProdidEqualsAndWaidEqualsOrderByPsidAsc(Integer prodid, Integer waid);
    List<ProductStock> findByOrderByPsidAsc(Pageable pageable);
    List<ProductStock> findByProdidEqualsAllIgnoreCase(Integer prodid);

    List<ProductStock> findByProdidEqualsAllIgnoreCaseOrderByPsidDesc(Integer prodid);

    List<ProductStock> findByProdidEqualsAndWaidEqualsAllIgnoreCaseOrderByPsidDesc(Integer prodid, Integer waid);


}
