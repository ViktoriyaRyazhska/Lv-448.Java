package dao.interfaceDao;

import entities.Address;
import service.OrderService;

import java.util.Optional;

public interface AddressDaoInterface {

    void save(Address address);

    Optional<Address> findById(Long id);

    void update(Address address);
}
