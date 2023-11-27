package vn.edu.iuh.fit.www_lab05.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.www_lab05.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
