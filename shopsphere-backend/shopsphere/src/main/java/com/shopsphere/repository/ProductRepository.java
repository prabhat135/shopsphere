package com.shopsphere.repository;

import com.shopsphere.model.Product;
import com.shopsphere.model.Category;
import com.shopsphere.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    
    List<Product> findByIsFeaturedTrue();
    
    List<Product> findByIsNewTrue();
    
    List<Product> findByCategory(Category category);
    
    List<Product> findByGender(Gender gender);
    
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<Category> findAllDistinctCategories();
    
    @Query("SELECT DISTINCT p.gender FROM Product p")
    List<Gender> findAllDistinctGenders();
    
    @Query("SELECT DISTINCT s FROM Product p JOIN p.sizes s")
    List<String> findAllDistinctSizes();
    
    @Query("SELECT DISTINCT c FROM Product p JOIN p.colors c")
    List<String> findAllDistinctColors();
}
