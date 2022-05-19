package hr.tvz.ivanovic.hardwareapp.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByHardware_ArticleCode(String articleCode);

    List<Review>findAll();

}
