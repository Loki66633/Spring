package hr.tvz.ivanovic.hardwareapp.review;

import hr.tvz.ivanovic.hardwareapp.Hardware;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name="review")
public class Review implements Serializable {
    @Id
    @Column(name="id")
    private Long id;

    @Column(name="naslov")
    String naslov;

    @Column(name="tekst")
    String tekst;

    @Column(name="ocjena")
    int ocjena;

    @ManyToOne
    @JoinColumn(name="hardware_id")
    private Hardware hardware;

    public Review(Long id, String naslov, String tekst, int ocjena) {
        this.id = id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.ocjena = ocjena;
    }

    public Review() {

    }
}
