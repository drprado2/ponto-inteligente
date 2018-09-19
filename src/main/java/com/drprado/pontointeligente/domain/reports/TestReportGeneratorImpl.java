package com.drprado.pontointeligente.domain.reports;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TestReportGeneratorImpl implements TestReportGenerator {
    @PersistenceContext
    private EntityManager em;

    public List<TestReport> generate(){
        List<TestReport> report = em.createQuery("select tp from TestReport tp", TestReport.class).getResultList();
        return report;
    }
}
