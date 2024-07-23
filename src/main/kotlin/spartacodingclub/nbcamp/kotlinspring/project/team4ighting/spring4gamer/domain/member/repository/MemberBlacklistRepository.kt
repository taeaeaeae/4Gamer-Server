package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.MemberBlacklist
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.MemberBlacklistId
import java.util.UUID

interface MemberBlacklistRepository : JpaRepository<MemberBlacklist, MemberBlacklistId> {

    fun existsByIdSubjectIdAndIdTargetId(subjectId: UUID, targetId: UUID): Boolean
    fun findByIdSubjectIdAndIdTargetId(subjectId: UUID, targetId: UUID): MemberBlacklist?
}