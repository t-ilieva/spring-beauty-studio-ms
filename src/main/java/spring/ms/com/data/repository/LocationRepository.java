package spring.ms.com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.ms.com.data.entity.Category;
import spring.ms.com.data.entity.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByName(String name);
}
