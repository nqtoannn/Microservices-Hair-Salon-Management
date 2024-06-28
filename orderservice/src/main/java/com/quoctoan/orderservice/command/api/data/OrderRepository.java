package com.quoctoan.orderservice.command.api.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, String> {
    @Query(value = "select O from Orders O where O.customerId = :customerId")
    List<Orders> findAllByCustomerId(String customerId);

    @Query(value = "select O from Orders O where O.orderStatus = 'SUCCESS'")
    List<Orders> findAllOrderSuccess(String customerId);


    @Query(value = "SELECT YEAR(o.orderDate) as year, MONTH(o.orderDate) as month, SUM(o.totalPrice) as totalRevenue " +
            "FROM Orders o " +
            "WHERE YEAR(o.orderDate) = :year " +
            "AND o.orderStatus = 'SUCCESS' " +
            "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) " +
            "ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    List<Object[]> findMonthlyRevenueByYear(@Param("year") int year);

    @Query(value = "SELECT YEAR(o.orderDate) as year, MONTH(o.orderDate) as month, SUM(o.totalPrice) as totalRevenue " +
            "FROM Orders o " +
            "WHERE YEAR(o.orderDate) = :year " +
            "AND o.orderStatus = 'SUCCESS' "+
            "AND MONTH(o.orderDate) BETWEEN :startMonth AND :endMonth " +
            "GROUP BY YEAR(o.orderDate), MONTH(o.orderDate) " +
            "ORDER BY YEAR(o.orderDate), MONTH(o.orderDate)")
    List<Object[]> findMonthlyRevenueByYearAndMonths(@Param("year") int year,
                                                     @Param("startMonth") int startMonth,
                                                     @Param("endMonth") int endMonth);
}
