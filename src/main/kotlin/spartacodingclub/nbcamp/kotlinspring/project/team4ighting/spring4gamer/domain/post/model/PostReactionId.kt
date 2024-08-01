package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Embeddable
class PostReactionId (
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    var post: Post? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    var member: Member? = null
)