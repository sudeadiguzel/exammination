package com.cloud.examsystem.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.Map;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatatableRequest {
//    private ArrayList<ColumnData> columns;
//    private List<Map<String, Object>> order;
    private int draw;
    private int start;
    private int length;

    private Map<String, Object> search;

    public PageRequest toPageRequest() {
//        ObjectMapper o = new ObjectMapper();
//        if (order != null && order.size() > 0) {
//            int columnIndex = (int) order.get(0).getOrDefault("column", -1);
//            String direction = (String) order.get(0).getOrDefault("dir", "asc");
//
//            ColumnData columnData = columns.get(columnIndex);
////            try {
////                columnData = o.treeToValue(columns.get(columnIndex), ColumnData.class);
////            } catch (JsonProcessingException e) {
////                return PageRequest.of(getStart() / getLength(), getLength());
////            }
//
//            if (columnData != null && columnData.getData() != null) {
//                return PageRequest.of(getStart() / getLength(), getLength(), Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Order.asc(columnData.getData()) : Sort.Order.desc(columnData.getData())));
//            }
//        }

        return PageRequest.of(getStart() / getLength(), getLength());
    }

    public String getSearchValue() {
        return getSearch().getOrDefault("value", "").toString();
    }
}
