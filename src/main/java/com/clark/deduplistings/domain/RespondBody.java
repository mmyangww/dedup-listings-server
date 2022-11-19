package com.clark.deduplistings.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespondBody {
    private String query_id;
    private String placekey;
    private String error;
}
