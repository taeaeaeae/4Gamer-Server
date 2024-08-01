package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Embeddable
class PostTagId(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_name", nullable = false)
    val tag: Tag
)