package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity(name = "channelblacklist")
class ChannelBlackList {
    @EmbeddedId
    val channelBlacklistId: ChannelBlackListId = ChannelBlackListId()

    companion object {
        fun doBlack(channel: Channel, member: Member): ChannelBlackList {
            val channelBlackList = ChannelBlackList()
            channelBlackList.channelBlacklistId.channel = channel
            channelBlackList.channelBlacklistId.member = member
            return channelBlackList
        }
    }
}