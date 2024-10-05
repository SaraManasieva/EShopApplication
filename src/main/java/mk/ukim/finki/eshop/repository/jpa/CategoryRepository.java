package mk.ukim.finki.eshop.repository.jpa;

import mk.ukim.finki.eshop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category>findAllByNameLike(String text);
    void deleteByName(String name);
}
