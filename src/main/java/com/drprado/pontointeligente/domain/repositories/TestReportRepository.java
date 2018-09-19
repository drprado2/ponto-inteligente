package com.drprado.pontointeligente.domain.repositories;

import com.drprado.pontointeligente.domain.reports.TestReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface TestReportRepository extends JpaRepository<TestReport, UUID>, JpaSpecificationExecutor<TestReport> {
}
