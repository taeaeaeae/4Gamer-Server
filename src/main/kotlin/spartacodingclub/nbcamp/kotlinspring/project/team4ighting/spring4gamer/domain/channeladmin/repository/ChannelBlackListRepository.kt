package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlackList
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model.ChannelBlackListId

interface ChannelBlackListRepository : JpaRepository<ChannelBlackList, ChannelBlackListId> {
    fun findByChannelBlacklistId(channelBlackListId: ChannelBlackListId): ChannelBlackList?
}