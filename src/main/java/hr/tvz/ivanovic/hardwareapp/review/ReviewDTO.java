package hr.tvz.ivanovic.hardwareapp.review;

import lombok.Data;

@Data
public class ReviewDTO {

    private String title;
    private String text;
    private int rating;

    public ReviewDTO(String title, String text,int rating) {
        this.title = title;
        this.text = text;
        this.rating = rating;
    }
}
