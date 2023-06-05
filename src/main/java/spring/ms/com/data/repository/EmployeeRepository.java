package spring.ms.com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.ms.com.data.entity.Employee;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @Query(value = "SELECT * FROM employee a WHERE a.first_name = :first_name and a.last_name = :last_name", nativeQuery = true)
    Optional<Employee> findByName(@Param("first_name") String firstName, @Param("last_name") String lastName);
}
