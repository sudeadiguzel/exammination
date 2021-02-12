package com.cloud.examsystem.common.util;

import com.cloud.examsystem.common.dto.DatatableRequest;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public class PaginationUtils {
    public static Map createResultSet(Page queryResult, DatatableRequest request) {
        HashMap<String, Object> result = new HashMap<>();
        result.put("draw", request.getDraw());
        result.put("recordsTotal", queryResult.getTotalElements());
        result.put("recordsFiltered", queryResult.getTotalElements());
        result.put("data", queryResult.getContent());

        return result;
    }
}
