package hr.tvz.ivanovic.hardwareapp;

import java.util.List;
import java.util.Optional;

public interface HardwareRepository {

    List<Hardware> findAll();

    Optional<Hardware> findByCode(String code);

    Optional<Hardware> save (Hardware hardware);

    List<Hardware> findIfInStock();

    boolean delete (String code);
}
