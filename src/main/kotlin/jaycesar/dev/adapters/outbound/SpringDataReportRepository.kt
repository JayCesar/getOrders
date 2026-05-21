package jaycesar.dev.adapters.outbound

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SpringDataReportRepository : JpaRepository<DeliveryReportEntity, Long> {

    @Query("SELECT e FROM DeliveryReportEntity e ORDER BY e.createdAt DESC")
    fun findSummaries(pageable: Pageable): List<DeliveryReportEntity>

    @Query("SELECT e FROM DeliveryReportEntity e ORDER BY e.createdAt ASC")
    fun findTrend(pageable: Pageable): List<DeliveryReportEntity>
}
