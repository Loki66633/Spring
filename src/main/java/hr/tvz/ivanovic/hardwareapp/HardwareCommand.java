package hr.tvz.ivanovic.hardwareapp;

import lombok.Data;

import javax.validation.constraints.*;

@Data
public class HardwareCommand {

    @NotBlank(message = "The name of the article must not be blank!")
    private String name;

    @NotBlank(message = "You must provide the article code!")
    @Pattern(message = "The article code must have 5 digits and must contain only numbers!", regexp = "^[0-9]{5}$")
    //@Digits(message = "The article code must contain only numbers and must be consisted of 5 digits!", integer = 5, fraction = 0)
    private String code;

    @NotNull(message = "You need to provide the type of the article!")
    private Hardware.Type type;

    @NotNull(message = "You must provide the quantity of the article in stock!")
    @PositiveOrZero(message = "The number of articles in stock must be positive or zero!")
    private Integer stock;

    @NotNull(message = "You must provide the price of the article!")
    @Positive(message = "The price of the article must be positive!")
    private Double price;

}
