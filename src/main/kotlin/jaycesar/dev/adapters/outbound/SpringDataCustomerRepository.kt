package jaycesar.dev.adapters.outbound

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

interface RegionCount {
    val region: String?
    val count: Long
}

interface CarrierCount {
    val carrier: String?
    val count: Long
}

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

    @Query("""
        SELECT region, COUNT(*) as count
        FROM affected_customers
        WHERE report_id = :reportId
        AND region IS NOT NULL
        GROUP BY region
        ORDER BY count DESC
    """, nativeQuery = true)
    fun countGroupByRegion(@Param("reportId") reportId: Long): List<RegionCount>

    @Query("""
        SELECT carrier, COUNT(*) as count
        FROM affected_customers
        WHERE report_id = :reportId
        AND carrier IS NOT NULL
        GROUP BY carrier
        ORDER BY count DESC
    """, nativeQuery = true)
    fun countGroupByCarrier(@Param("reportId") reportId: Long): List<CarrierCount>
}
