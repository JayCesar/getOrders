package jaycesar.dev.adapters.inbound

import jaycesar.dev.core.ports.ReportService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/reports")
class ReportController(private val service: ReportService) {

    @GetMapping
    fun listReports(
        @RequestParam(defaultValue = "20") limit: Int,
        @RequestParam(defaultValue = "0") offset: Int
    ) = service.listReports(limit, offset)

    @GetMapping("/trend")
    fun getTrend(
        @RequestParam(defaultValue = "10") limit: Int
    ) = service.getTrend(limit)

    @GetMapping("/{id}")
    fun getReport(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getReport(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/regions")
    fun getRegions(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getRegions(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/carriers")
    fun getCarriers(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getCarriers(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }

    @GetMapping("/{id}/reasons")
    fun getReasons(@PathVariable id: Long): ResponseEntity<Any> = try {
        ResponseEntity.ok(service.getReasons(id))
    } catch (e: NoSuchElementException) {
        ResponseEntity.notFound().build()
    }
}
