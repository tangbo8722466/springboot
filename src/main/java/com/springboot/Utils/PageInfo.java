package com.springboot.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Integer pageNumber;
    private Integer pageSize;
    private Long total;
}
