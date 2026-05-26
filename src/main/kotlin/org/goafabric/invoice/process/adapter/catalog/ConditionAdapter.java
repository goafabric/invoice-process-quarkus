package org.goafabric.invoice.process.adapter.catalog;

import jakarta.ws.rs.QueryParam;

public interface ConditionAdapter {
    Condition findByCode(@QueryParam("code") String code);
}