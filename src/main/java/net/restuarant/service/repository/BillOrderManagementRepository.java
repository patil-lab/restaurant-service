package net.restuarant.service.repository;

import net.restuarant.service.entity.BillOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillOrderManagementRepository extends CrudRepository<BillOrder, Long> {


}
