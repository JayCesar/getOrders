package jaycesar.dev.adapters.outbound

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SpringDataCustomerRepository :
    JpaRepository<AffectedCustomerEntity, Long>,
    JpaSpecificationExecutor<AffectedCustomerEntity> {

    @Modifying
    @Query("""
        UPDATE AffectedCustomerEntity c
        SET c.whatsappSent = true, c.whatsappSentAt = :sentAt
        WHERE c.id = :id
    """)
    fun markSent(id: Long, sentAt: LocalDateTime): Int
}
