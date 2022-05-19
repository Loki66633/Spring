package hr.tvz.ivanovic.hardwareapp;

import lombok.Data;

@Data
public class HardwareDTO {

    private String name;
    private Double price;
    private String code;
    public HardwareDTO(String name, Double price, String code) {
        this.name = name;
        this.price = price;
        this.code = code;
    }
}
