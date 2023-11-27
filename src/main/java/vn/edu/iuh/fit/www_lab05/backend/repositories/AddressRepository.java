package vn.edu.iuh.fit.www_lab05.backend.repositories;

import org.springframework.data.repository.CrudRepository;
import vn.edu.iuh.fit.www_lab05.backend.models.Address;

public interface AddressRepository extends CrudRepository<Address, Long> {
}
