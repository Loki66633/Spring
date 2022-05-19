package hr.tvz.ivanovic.hardwareapp;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceImplement implements HardwareService{

    private final HardwareRepository hardwareRepository;

    public ServiceImplement(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }



    @Override
    public List<HardwareDTO> findAll() {
        return hardwareRepository.findAll().stream().map(this::mapHardwareToDTO).collect(Collectors.toList());
    }

    @Override
    public HardwareDTO findByCode(String code) {
        return hardwareRepository.findByCode(code).map(this::mapHardwareToDTO).orElse(null);
    }

    @Override
    public Optional<HardwareDTO> save(HardwareCommand command) {
        return hardwareRepository.save(acceptCommand(command)).map(this::mapHardwareToDTO);
    }

    @Override
    public boolean delete(String code) {
        if(hardwareRepository.delete(code))
            return true;
        return false;

    }

    private HardwareDTO mapHardwareToDTO(Hardware hardware){
        return new HardwareDTO(hardware.getName(),hardware.getPrice(), hardware.getArticleCode());
    }

    private Hardware acceptCommand(HardwareCommand command){
        return new Hardware(command.getName(), command.getCode(),command.getType(), command.getStock(), command.getPrice());
    }
}
