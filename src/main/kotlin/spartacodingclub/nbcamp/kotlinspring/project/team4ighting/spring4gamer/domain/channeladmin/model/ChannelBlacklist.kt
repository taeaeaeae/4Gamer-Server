package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channeladmin.model

import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.member.model.Member

@Entity(name = "channelblacklist")
class ChannelBlacklist (
    @EmbeddedId
    val id: ChannelBlacklistId
) {

    companion object {

        fun from(channel: Channel, member: Member): ChannelBlacklist =

            ChannelBlacklist(
               id = ChannelBlacklistId(
                   channel = channel,
                   member = member
               )
            )
    }
}