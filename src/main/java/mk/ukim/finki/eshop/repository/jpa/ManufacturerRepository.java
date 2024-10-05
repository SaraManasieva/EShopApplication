package mk.ukim.finki.eshop.repository.jpa;

import mk.ukim.finki.eshop.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
}
