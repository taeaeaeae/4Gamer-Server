package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity
@Table(name = "post_reaction")
class PostReaction private constructor (
    member: Member,
    post: Post,
    isUpvoting: Boolean
) {

    @EmbeddedId
    val id: PostReactionId = PostReactionId(
        member = member,
        post = post
    )

    @Column(name = "is_upvoting")
    var isUpvoting: Boolean = isUpvoting


    companion object {

        fun from(member: Member, post: Post, isUpvoting: Boolean): PostReaction =

            PostReaction(
                member = member,
                post = post,
                isUpvoting = isUpvoting
            )
    }
}