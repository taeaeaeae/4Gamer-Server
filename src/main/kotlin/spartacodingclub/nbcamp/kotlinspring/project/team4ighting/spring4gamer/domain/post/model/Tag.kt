package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.common.type.BaseTimeEntity

@Entity
@Table(name = "tag")
class Tag private constructor (
    name: String
) : BaseTimeEntity() {

    @Id
    val name: String = name


    companion object {

        fun from(name: String): Tag =

            Tag(name = name)
    }


    fun refresh() {
        preUpdate()
    }
}