package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.repository

import org.springframework.data.jpa.repository.JpaRepository
import spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.domain.comment.model.CommentReport

interface CommentReportRepository : JpaRepository<CommentReport, Long>