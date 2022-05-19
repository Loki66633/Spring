package hr.tvz.ivanovic.hardwareapp.review;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review")
@CrossOrigin(origins = "http://localhost:4200")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<ReviewDTO> getAllReviews(){
        return reviewService.findAll();
    }

    @GetMapping ("{code}")
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    public List<ReviewDTO> getAllReviewsByHardwareCode(@PathVariable String code){
        return reviewService.findAllByArticleCode(code);
    }
}
