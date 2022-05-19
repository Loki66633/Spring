package hr.tvz.ivanovic.hardwareapp;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("hardware")
@CrossOrigin(origins = "http://localhost:4200")
public class REST_Controller {

    private final HardwareService hardwareService;


    public REST_Controller(HardwareService hardwareService) {
        this.hardwareService = hardwareService;
    }
    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<HardwareDTO> getAllHardware(){
        return hardwareService.findAll();
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping ("/{code}")
    public ResponseEntity<HardwareDTO> getHardwareByCode(@PathVariable("code") String code){
        HardwareDTO returnValue = hardwareService.findByCode(code);
        if(returnValue!=null){

            return new ResponseEntity<>(returnValue,HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Article with provided code does not exist!",null);


    }
    @Secured({"ROLE_ADMIN"})
    @PostMapping
    public ResponseEntity<HardwareDTO> saveHardware(@Valid @RequestBody HardwareCommand command){

        return hardwareService.save(command)
                .map(HardwareDTO -> ResponseEntity.status(HttpStatus.CREATED).body(HardwareDTO))
                .orElseGet(()->ResponseEntity.status(HttpStatus.CONFLICT).build());

    }
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{code}")
    public ResponseEntity<?> deleteHardware(@PathVariable String code){
        if(hardwareService.delete(code)){
            return new ResponseEntity<>("Hardware successfully removed!",HttpStatus.NO_CONTENT);
        }
        else
         return new ResponseEntity<>("Hardware with the provided article code was not found!",HttpStatus.NO_CONTENT);
    }
}
