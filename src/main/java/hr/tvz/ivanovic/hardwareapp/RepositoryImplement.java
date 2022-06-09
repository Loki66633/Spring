package hr.tvz.ivanovic.hardwareapp;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RepositoryImplement implements HardwareRepository{


    private List<Hardware> HARDWARE_LIST = new LinkedList<Hardware>(Arrays.asList(
            new Hardware("RTX 3090","11111", Hardware.Type.GPU,6,16250.50),
            new Hardware("Ryzen 5 3600x","93612",Hardware.Type.CPU,15,2533.70),
            new Hardware("Gigabyte B450M","71894",Hardware.Type.MBO,21,496.95),
            new Hardware("Kingston Fury Beast 32GB","37712",Hardware.Type.RAM,11,1341.63),
            new Hardware("Samsung 980 250GB, M.2 NVMe","51262",Hardware.Type.STORAGE,7, 370.68),
            new Hardware("Corsair CX450M 450W","45285",Hardware.Type.OTHER,5,412.30))

    );

    @Override
    public List<Hardware> findAll() {
        return HARDWARE_LIST;
    }

    @Override
    public Optional<Hardware> findByCode(String code) {
        return HARDWARE_LIST.stream().filter(h -> h.getArticleCode().equals(code)).findAny();
    }

    @Override
    public Optional<Hardware> save(Hardware hardware) {
        boolean exists=false;
        for(int i=0;i<HARDWARE_LIST.size(); i++){
            if(HARDWARE_LIST.get(i).getArticleCode().equals(hardware.getArticleCode())){
                exists=true;
                break;
            }
        }
        if(!exists){
            HARDWARE_LIST.add(hardware);
            return Optional.of(hardware);

        }
        return Optional.empty();
    }

    @Override
    public List<Hardware> findIfInStock() {
        return HARDWARE_LIST.stream().filter(hardware -> hardware.getStock() >0).collect(Collectors.toList());
    }

    @Override
    public boolean delete(String code) {
        for(int i=0;i<HARDWARE_LIST.size();i++){
            if(HARDWARE_LIST.get(i).getArticleCode().equals(code)){
                HARDWARE_LIST.remove(i);
                return true;
            }
        }
        return false;
    }

}

/*
    JSON TEST DATA
{
        "name": "RTX 3090",
        "articleCode": "12345",
        "price": 125.0,
        "articleType": "GPU",
        "stock":2
    }*/