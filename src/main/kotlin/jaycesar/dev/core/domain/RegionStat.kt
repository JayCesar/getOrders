package jaycesar.dev.core.domain

data class RegionStat(val region: String, val failures: Long, val failureRate: Double)
data class CarrierStat(val carrier: String, val failures: Long, val failureRate: Double)
