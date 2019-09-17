package com.springboot.Vo.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class Empty implements Serializable {
    private String result = "empty";
}
