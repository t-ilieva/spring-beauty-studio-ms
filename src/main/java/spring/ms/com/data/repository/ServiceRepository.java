package spring.ms.com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.ms.com.data.entity.Employee;
import spring.ms.com.data.entity.Service;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    @Query(value = "SELECT * FROM service a WHERE a.id = category.id AND category.name = :category", nativeQuery = true)
    List<Service> findByCategory(@Param("category") String category);
}
