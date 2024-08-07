package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlacklist
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlacklistId

interface ChannelBlacklistRepository : JpaRepository<ChannelBlacklist, ChannelBlacklistId> {

    @Query("select cb from channelblacklist cb join fetch cb.id.member where cb.id.channel.id = :channelId")
    fun findByChannelId(channelId: Long): List<ChannelBlacklist>
}