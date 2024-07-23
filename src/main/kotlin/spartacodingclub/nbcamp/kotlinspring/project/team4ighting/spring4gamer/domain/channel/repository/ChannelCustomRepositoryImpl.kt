package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository

import com.querydsl.jpa.JPAExpressions
import org.springframework.stereotype.Repository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.board.model.QBoard
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.QComment
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.Post
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.QPost
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.querydsl.QueryDslSupport
import java.time.ZoneId
import java.time.ZonedDateTime

@Repository
class ChannelCustomRepositoryImpl : ChannelCustomRepository, QueryDslSupport() {

    private val board = QBoard.board
    private val post = QPost.post
    private val comment = QComment.comment

    override fun findTopPosts(channelId: Long): List<Post> {
        val boardId = JPAExpressions
            .select(board.id)
            .from(board)
            .where(board.channel.id.eq(channelId))

        val result = queryFactory
            .select(post)
            .from(post)
            .where(
                post.createdAt.gt(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(7))
                    .and(post.board.id.`in`(boardId))
            )
            .leftJoin(comment).on(post.id.eq(comment.post.id))
            .groupBy(post.id)
            .orderBy(
                post.upvotes.sum().desc(),
                comment.post.count().desc(),
                post.createdAt.desc()
            )
            .limit(10)
            .fetch()

        return result
    }
}