CREATE DATABASE db_cb4cards CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ─────────────────────────────────────────────────
-- delivery_reports
-- One row per CSV analysis run
-- ─────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS delivery_reports (
                                                id                        INT UNSIGNED    NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                source_file               TEXT            NOT NULL,
                                                analysis_file             TEXT            NOT NULL,
                                                risk_level                VARCHAR(10)     NOT NULL,
    executive_summary         TEXT,
    total_deliveries          INT             NOT NULL DEFAULT 0,
    total_failures            INT             NOT NULL DEFAULT 0,
    total_successes           INT             NOT NULL DEFAULT 0,
    failure_rate_percent      DECIMAL(5,2),
    success_rate_percent      DECIMAL(5,2),
    average_delivery_attempts DECIMAL(6,2),
    critical_regions          JSON,
    critical_carriers         JSON,
    top_failure_reasons       JSON,
    operational_patterns      JSON,
    customer_risks            JSON,
    fraud_indicators          JSON,
    recommendations           JSON,
    suggested_actions         JSON,
    created_at                DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_risk_level  (risk_level),
    INDEX idx_created_at  (created_at)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ─────────────────────────────────────────────────
-- affected_customers
-- One row per failed customer per report
-- ─────────────────────────────────────────────────
CREATE TABLE IF NOT EXISTS affected_customers (
                                                  id               INT UNSIGNED    NOT NULL AUTO_INCREMENT PRIMARY KEY,
                                                  report_id        INT UNSIGNED    NOT NULL,
                                                  name             VARCHAR(255),
    whatsapp         VARCHAR(20),
    failure_reason   TEXT,
    region           VARCHAR(100),
    whatsapp_sent    BOOL            NOT NULL DEFAULT FALSE,
    whatsapp_sent_at DATETIME,
    created_at       DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_customer_report FOREIGN KEY (report_id)
    REFERENCES delivery_reports (id) ON DELETE CASCADE,
    INDEX idx_report_id    (report_id),
    INDEX idx_whatsapp     (whatsapp),
    INDEX idx_region       (region),
    INDEX idx_whatsapp_sent (whatsapp_sent)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


USE db_cb4cards;
-- paste contents of schema.sql here

ALTER TABLE affected_customers
    ADD COLUMN carrier VARCHAR(100) NULL
AFTER region;

-- other alter statements added here as needed
ALTER TABLE affected_customers
    ADD COLUMN delivery_attempted_at DATETIME NULL AFTER carrier,
  ADD INDEX idx_delivery_attempted_at (delivery_attempted_at);

ALTER TABLE delivery_reports
    ADD COLUMN delivery_date_from DATETIME NULL AFTER suggested_actions,
    ADD COLUMN delivery_date_to   DATETIME NULL AFTER delivery_date_from,
    ADD INDEX idx_delivery_date_from (delivery_date_from);