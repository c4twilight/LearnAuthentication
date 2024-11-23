package com.example.LearnAuthentication.dto;
import lombok.*;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSearchAndFilterRequest extends PaginationRequest {
    // this is the lookup text for search
    private String searchText;
    private Date startDate;
    private Date endDate;

}