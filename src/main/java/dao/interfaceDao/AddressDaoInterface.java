package dao.interfaceDao;

import entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDaoInterface {

    void save(Address address);

    Optional<Address> findById(Long id);

    void update(Address address);

}
