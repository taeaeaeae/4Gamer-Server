package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.GameReview

interface GameReviewRepository : JpaRepository<GameReview, Long> {

    @Query("""
        select * 
        from game_review 
        where timestampdiff(day, created_at, now()) < 7 
            and upvotes > 0 
        order by upvotes desc, created_at desc
        limit 10
    """, nativeQuery = true)
    fun findTopGameReviews(): List<GameReview>
}