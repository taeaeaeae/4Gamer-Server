package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Message
import java.util.UUID

interface MessageRepository : JpaRepository<Message, Long> {

    fun findAllBySubjectId(subjectId: UUID): List<Message>
    fun findAllBySubjectIdAndTargetId(subjectId: UUID, targetId: UUID): List<Message>

    fun findByTargetId(targetId: UUID): List<Message>
}