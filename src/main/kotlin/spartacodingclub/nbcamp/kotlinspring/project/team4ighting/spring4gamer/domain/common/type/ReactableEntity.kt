package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass

@MappedSuperclass
open class ReactableEntity : BaseTimeEntity() {

    @Column(name = "upvotes", nullable = false)
    var upvotes: Long = 0

    @Column(name = "downvotes", nullable = false)
    var downvotes: Long = 0


    fun increaseReaction(isReactionUpvoting: Boolean) {

        if (isReactionUpvoting)
            this.increaseUpvote()
        else
            this.increaseDownvote()
    }

    fun decreaseReaction(isReactionUpvoting: Boolean) {

        if (isReactionUpvoting)
            this.decreaseUpvote()
        else
            this.decreaseDownvote()
    }

    fun applySwitchedReaction(isNewReactionUpvoting: Boolean) {

        this.decreaseReaction(!isNewReactionUpvoting)
        this.increaseReaction(isNewReactionUpvoting)
    }


    private fun increaseUpvote() {

        this.upvotes += 1
    }

    private fun increaseDownvote() {

        this.downvotes += 1
    }

    private fun decreaseUpvote() {

        this.upvotes -= 1
    }

    private fun decreaseDownvote() {

        this.downvotes -= 1
    }
}