package com.quoctoan.hairservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface ServiceHairRepository extends JpaRepository<ServiceHair, String>{
    @Query(value = "SELECT S FROM ServiceHair S WHERE S.serviceName LIKE CONCAT('%', :serviceName, '%')")
    List<ServiceHair> findByPartialServiceName(@Param("serviceName") String serviceName);

    @Transactional
    @Modifying
    @Query("UPDATE ServiceHair s SET s.url = :image where s.id = :serviceHairId")
    void updateImage(String image, String serviceHairId);

}

