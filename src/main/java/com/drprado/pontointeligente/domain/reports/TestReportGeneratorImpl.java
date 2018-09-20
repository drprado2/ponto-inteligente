package com.drprado.pontointeligente.domain.reports;

import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.GenericFilters;
import com.drprado.pontointeligente.crosscutting.util.QuerySpecificationAPI.QuerySpecificationResolver;
import com.drprado.pontointeligente.domain.querySpecifications.TestReportQuery;
import com.drprado.pontointeligente.domain.repositories.TestReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TestReportGeneratorImpl implements TestReportGenerator {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    TestReportRepository testReportRepository;

    @Autowired
    private TestReportQuery testReportQuery;

    @Override
    public List<TestReport> generateEM() {
        return em.createQuery("select tp from TestReport tp", TestReport.class).getResultList();
    }

    @Override
    public List<TestReport> generateRepo() {
        return testReportRepository.findAll();
    }

    @Override
    public List<TestReport> generateRepo(GenericFilters filters) {
        Specification<TestReport> finalSpec = QuerySpecificationResolver.resolveFilters(testReportQuery, filters);
        return testReportRepository.findAll(finalSpec);
    }
}
