package com.example.employeemanagementapp.repository;

import com.example.employeemanagementapp.entities.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {
   Page<Employee> findByNameContains(String keyword, Pageable pageable);

   //utilisons JPQL
   //@Query("select e from Employee e where e.name like %:x%")
   // List<Employee> find(@Param("x") String keyword);
}
