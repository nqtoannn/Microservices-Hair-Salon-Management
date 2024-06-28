package com.ngocthong.userservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	User findByEmail(String email);

	@Query(value = "select U from User U where U.role = 'EMPLOYEE'")
	List<User> findAllEmployees();

	@Query(value = "select U from User U where U.role = 'CUSTOMER'")
	List<User> findAllCustomer();

}
