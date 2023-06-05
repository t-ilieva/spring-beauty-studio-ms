package spring.ms.com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.ms.com.data.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

}
