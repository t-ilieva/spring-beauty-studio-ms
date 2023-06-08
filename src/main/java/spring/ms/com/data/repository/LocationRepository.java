package spring.ms.com.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import spring.ms.com.data.entity.Location;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findByName(String name);

    @Query(value = "SELECT * FROM location a WHERE a.address = :address", nativeQuery = true)
    Optional<Location> findByAddress(@Param("address") String address);
}
