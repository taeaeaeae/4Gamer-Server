package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.channel.model.Channel

interface ChannelRepository : JpaRepository<Channel, Long> {

    fun findAllBy(pageable: Pageable): Slice<Channel>
}