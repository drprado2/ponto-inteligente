package com.drprado.pontointeligente.domain.reports;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilters;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TestReportGenerator {
    List<TestReport> generateEM();

    List<TestReport> generateRepo();

    List<TestReport> generateRepo(GenericFilters filters);
}
