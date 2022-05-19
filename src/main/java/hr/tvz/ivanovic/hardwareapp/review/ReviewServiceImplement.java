package hr.tvz.ivanovic.hardwareapp.review;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReviewServiceImplement implements ReviewService{

    private final ReviewRepository reviewRepository;

    public ReviewServiceImplement(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<ReviewDTO> findAll() {
        return reviewRepository.findAll().stream().map(this::mapReviewToDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> findAllByArticleCode(String code) {
        return reviewRepository.findAllByHardware_ArticleCode(code).stream().map(this::mapReviewToDTO).collect(Collectors.toList());
    }

    private ReviewDTO mapReviewToDTO(Review review){
        return new ReviewDTO(review.naslov,review.tekst, review.ocjena);
    }
}
