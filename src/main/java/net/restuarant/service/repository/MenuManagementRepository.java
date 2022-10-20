package net.restuarant.service.repository;

import net.restuarant.service.entity.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuManagementRepository	extends CrudRepository<MenuItem,Long> {
	Optional<MenuItem> findByName(String name);

}
