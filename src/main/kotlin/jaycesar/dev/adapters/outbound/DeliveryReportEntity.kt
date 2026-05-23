package jaycesar.dev.adapters.outbound

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "delivery_reports")
class DeliveryReportEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "source_file", columnDefinition = "TEXT")
    val sourceFile: String = "",

    @Column(name = "analysis_file", columnDefinition = "TEXT")
    val analysisFile: String = "",

    @Column(name = "risk_level", length = 10)
    val riskLevel: String = "",

    @Column(name = "executive_summary", columnDefinition = "TEXT")
    val executiveSummary: String? = null,

    @Column(name = "total_deliveries")
    val totalDeliveries: Int = 0,

    @Column(name = "total_failures")
    val totalFailures: Int = 0,

    @Column(name = "total_successes")
    val totalSuccesses: Int = 0,

    @Column(name = "failure_rate_percent", precision = 5, scale = 2)
    val failureRatePercent: BigDecimal? = null,

    @Column(name = "success_rate_percent", precision = 5, scale = 2)
    val successRatePercent: BigDecimal? = null,

    @Column(name = "average_delivery_attempts", precision = 6, scale = 2)
    val averageDeliveryAttempts: BigDecimal? = null,

    @Column(name = "critical_regions", columnDefinition = "JSON")
    val criticalRegions: String? = null,

    @Column(name = "critical_carriers", columnDefinition = "JSON")
    val criticalCarriers: String? = null,

    @Column(name = "top_failure_reasons", columnDefinition = "JSON")
    val topFailureReasons: String? = null,

    @Column(name = "operational_patterns", columnDefinition = "JSON")
    val operationalPatterns: String? = null,

    @Column(name = "customer_risks", columnDefinition = "JSON")
    val customerRisks: String? = null,

    @Column(name = "fraud_indicators", columnDefinition = "JSON")
    val fraudIndicators: String? = null,

    @Column(name = "recommendations", columnDefinition = "JSON")
    val recommendations: String? = null,

    @Column(name = "suggested_actions", columnDefinition = "JSON")
    val suggestedActions: String? = null,

    @Column(name = "delivery_date_from")
    val deliveryDateFrom: LocalDateTime? = null,

    @Column(name = "delivery_date_to")
    val deliveryDateTo: LocalDateTime? = null,

    @Column(name = "created_at", updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
