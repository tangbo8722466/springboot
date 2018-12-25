package com.springboot.Utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo implements Serializable{
    private Integer pageNumber;
    private Integer pageSize;
    private Long total;
}
