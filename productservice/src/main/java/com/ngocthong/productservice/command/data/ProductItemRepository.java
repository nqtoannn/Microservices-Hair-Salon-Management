package com.ngocthong.productservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ProductItemRepository extends JpaRepository<ProductItem, String> {
    @Transactional
    @Modifying
    @Query("UPDATE ProductItem p SET p.imageUrl = :image where p.id = :productItemId")
    void updateImage(String image, String productItemId);
}
