package Sber.Sber.repositories;


import Sber.Sber.models.Realty;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface RealtyRepository extends JpaRepository<Realty, Long>{
    Optional<Realty> findByRealtyname(String realtyname);

}
