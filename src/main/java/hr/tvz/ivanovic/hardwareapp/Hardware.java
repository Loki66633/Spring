package hr.tvz.ivanovic.hardwareapp;

import hr.tvz.ivanovic.hardwareapp.review.Review;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="hardware")
public class Hardware implements Serializable {

    @Id
    @Column(name="id")
    private Long id;


    public enum Type {CPU,GPU,MBO,RAM,STORAGE,OTHER}

    @Column(name="name")
    private String name;

    @Column(name="article_Code")
    private String articleCode;

    @Enumerated(EnumType.STRING)
    @Column(name="articleType")
    private Type articleType;

    @Column(name="stock")
    private Integer stock;

    @Column(name="price")
    private Double price;

    @OneToMany(mappedBy = "hardware", fetch = FetchType.EAGER)
    public List<Review> reviews;

    public Hardware(String name, String articleCode, Type articleType, Integer stock, Double price) {
        this.name = name;
        this.articleCode = articleCode;
        this.articleType = articleType;
        this.stock = stock;
        this.price = price;
    }
    public Hardware(){}


}
