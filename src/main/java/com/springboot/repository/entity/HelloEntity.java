package com.springboot.repository.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Created by tangbo on 2018/1/30 0030.
 */
@Entity(name = "hello")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelloEntity {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "name", length = 64, nullable = false)
    private String name;

    @Column(name = "remark", length = 64, nullable = false)
    private String remark;
}
