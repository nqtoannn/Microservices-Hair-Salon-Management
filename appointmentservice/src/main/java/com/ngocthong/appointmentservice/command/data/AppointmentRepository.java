package com.ngocthong.appointmentservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    @Query(value = "select A from Appointment A where A.customerId = :customerId")
    List<Appointment> findAllByCustomerId(String customerId);
    @Query("SELECT A FROM Appointment A " +
            "WHERE A.userId = :employeeId "+
            "AND  A.status = 'WAITING'")
    List<Appointment> findAppointmentForEmployee(@Param("employeeId") String employeeId);

    @Query("SELECT A FROM Appointment A " +
            "WHERE A.userId = :employeeId ")
    List<Appointment> findAllAppointmentForEmployee(@Param("employeeId") String employeeId);

    @Query("SELECT A FROM Appointment A " +
            "WHERE A.userId = :employeeId " +
            "AND ( A.status = 'SUCCESS' OR A.status = 'REVIEWED') " +
            "ORDER BY FUNCTION('ABS', FUNCTION('TIMESTAMPDIFF', SECOND, " +
            "TIMESTAMP(CONCAT(A.appointmentDate, ' ', A.appointmentTime)), CURRENT_TIMESTAMP()))")
    List<Appointment> findAppointmentDoneByEmployee(@Param("employeeId") String employeeId);
    @Query("SELECT MONTH(a.appointmentDate) as month, SUM(a.price) as totalRevenue " +
            "FROM Appointment a " +
            "WHERE ( a.status = 'SUCCESS' OR a.status = 'REVIEWED') " +
            "AND YEAR(a.appointmentDate) = :year " +
            "GROUP BY MONTH(a.appointmentDate) " +
            "ORDER BY MONTH(a.appointmentDate)")
    List<Object[]> findMonthlyRevenueByYear(@Param("year") int year);

    @Query("SELECT YEAR(a.appointmentDate) as year, MONTH(a.appointmentDate) as month, SUM(a.price) as totalRevenue " +
            "FROM Appointment a " +
            "WHERE ( a.status = 'SUCCESS' OR a.status = 'REVIEWED') " +
            "AND (YEAR(a.appointmentDate) = :year " +
            "AND MONTH(a.appointmentDate) BETWEEN :startMonth AND :endMonth) " +
            "GROUP BY YEAR(a.appointmentDate), MONTH(a.appointmentDate) " +
            "ORDER BY YEAR(a.appointmentDate), MONTH(a.appointmentDate)")
    List<Object[]> findMonthlyRevenueByYearAndMonths(@Param("year") int year,
                                                     @Param("startMonth") int startMonth,
                                                     @Param("endMonth") int endMonth);


}
