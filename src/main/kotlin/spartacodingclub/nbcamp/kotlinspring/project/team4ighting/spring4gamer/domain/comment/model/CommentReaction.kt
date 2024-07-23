package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity
@Table(name = "comment_reaction")
class CommentReaction private constructor (
    member: Member,
    comment: Comment,
    isUpvoting: Boolean
) {

    @EmbeddedId
    val id: CommentReactionid = CommentReactionid(
        member = member,
        comment = comment
    )

    @Column(name = "is_upvoting")
    var isUpvoting: Boolean = isUpvoting


    companion object {

        fun from(member: Member, comment: Comment, isUpvoting: Boolean): CommentReaction =

            CommentReaction(
                member = member,
                comment = comment,
                isUpvoting = isUpvoting
            )
    }
}