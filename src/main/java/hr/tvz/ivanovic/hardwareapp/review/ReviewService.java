package hr.tvz.ivanovic.hardwareapp.review;

import java.util.List;

public interface ReviewService {

    List<ReviewDTO> findAll();

    List<ReviewDTO> findAllByArticleCode(String code);
}
