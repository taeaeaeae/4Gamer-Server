package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.service

import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.dto.response.GameReviewReactionResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.gamereview.repository.GameReviewReactionRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MemberSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.dto.response.MessageResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.MemberBlacklist
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Message
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.toSimplifiedResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberBlacklistRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MemberRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.repository.MessageRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.dto.response.PostResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.model.toResponse
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.post.repository.PostRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.exception.ModelNotFoundException
import java.util.*

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberBlacklistRepository: MemberBlacklistRepository,
    private val messageRepository: MessageRepository,
    private val gameReviewReactionRepository: GameReviewReactionRepository,
    private val postRepository: PostRepository
) {

    fun getMember(id: UUID): MemberResponse =

        memberRepository.findByIdOrNull(id)
            ?.toResponse()
            ?: throw EntityNotFoundException("model not found")


    fun getSimpleMember(id: UUID): MemberSimplifiedResponse =

        memberRepository.findByIdOrNull(id)
            ?.toSimplifiedResponse()
            ?: throw EntityNotFoundException("model not found")


    fun addMessage(
        memberId: UUID,
        targetId: UUID,
        message: String
    ): MessageResponse {

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)
        val target = memberRepository.findByIdOrNull(targetId)
            ?: throw ModelNotFoundException("Member", targetId)

        return messageRepository.save(
            Message.from(
                subject = member,
                target = target,
                message = message
            )
        ).toResponse()
    }


    fun addBlacklist(
        memberId: UUID,
        targetId: UUID
    ) {

        val member = memberRepository.findByIdOrNull(memberId)
            ?: throw ModelNotFoundException("Member", memberId)
        val target = memberRepository.findByIdOrNull(targetId)
            ?: throw ModelNotFoundException("Member", targetId)

        if (memberBlacklistRepository.existsByIdSubjectIdAndIdTargetId(memberId, targetId))
            throw IllegalArgumentException("Blacklist already added")

        memberBlacklistRepository.save(
            MemberBlacklist.from(
                subject = member,
                target = target
            )
        )
    }


    fun removeBlacklist(
        memberId: UUID,
        targetId: UUID
    ) {

        val targetMemberBlacklist = memberBlacklistRepository.findByIdSubjectIdAndIdTargetId(memberId, targetId)
            ?: throw ModelNotFoundException("MemberBlacklist", "${memberId}/${targetId}")

        memberBlacklistRepository.delete(targetMemberBlacklist)
    }


    fun getGameReviewReactionList(memberId: UUID): List<GameReviewReactionResponse> =

        gameReviewReactionRepository.findByMemberId(memberId).map { it.toResponse() }


    fun getPostList(memberId: UUID): List<PostResponse> =

        postRepository.findByMemberId(memberId).map { it.toResponse() }
}