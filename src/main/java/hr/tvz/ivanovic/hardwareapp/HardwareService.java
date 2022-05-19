package hr.tvz.ivanovic.hardwareapp;

import java.util.List;
import java.util.Optional;

public interface HardwareService {

    List<HardwareDTO> findAll();

    HardwareDTO findByCode(String code);

    Optional<HardwareDTO> save (HardwareCommand command);

    boolean delete (String code);
}
