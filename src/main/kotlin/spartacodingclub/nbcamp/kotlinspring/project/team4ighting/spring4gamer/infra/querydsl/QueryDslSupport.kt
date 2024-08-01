package spartacodingclub.nbcamp.kotlinspring.project.team4ighting.spring4gamer.infra.querydsl

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext

abstract class QueryDslSupport {

    @PersistenceContext
    protected lateinit var entityManager: EntityManager

    protected val queryFactory: JPAQueryFactory
        get() = JPAQueryFactory(entityManager)
}