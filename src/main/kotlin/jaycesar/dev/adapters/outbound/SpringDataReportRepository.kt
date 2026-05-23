package jaycesar.dev.adapters.outbound

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface SpringDataReportRepository : JpaRepository<DeliveryReportEntity, Long> {

    @Query("SELECT e FROM DeliveryReportEntity e ORDER BY COALESCE(e.deliveryDateFrom, e.createdAt) DESC")
    fun findSummaries(pageable: Pageable): List<DeliveryReportEntity>

    @Query("SELECT e FROM DeliveryReportEntity e ORDER BY COALESCE(e.deliveryDateFrom, e.createdAt) ASC")
    fun findTrend(pageable: Pageable): List<DeliveryReportEntity>

    @Query("""
        SELECT e FROM DeliveryReportEntity e
        WHERE COALESCE(e.deliveryDateFrom, e.createdAt) >= :from
          AND COALESCE(e.deliveryDateFrom, e.createdAt) <= :to
        ORDER BY COALESCE(e.deliveryDateFrom, e.createdAt) DESC
    """)
    fun findByDeliveryDateRange(
        @Param("from") from: LocalDateTime,
        @Param("to") to: LocalDateTime
    ): List<DeliveryReportEntity>

    @Query("""
        SELECT e FROM DeliveryReportEntity e
        WHERE COALESCE(e.deliveryDateFrom, e.createdAt) >= :from
          AND COALESCE(e.deliveryDateFrom, e.createdAt) <= :to
        ORDER BY COALESCE(e.deliveryDateFrom, e.createdAt) ASC
    """)
    fun findByDeliveryDateRangeAsc(
        @Param("from") from: LocalDateTime,
        @Param("to") to: LocalDateTime
    ): List<DeliveryReportEntity>
}
