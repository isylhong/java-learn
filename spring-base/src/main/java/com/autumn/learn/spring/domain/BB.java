package com.autumn.learn.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yl
 * @since 2022-11-14 23:07
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BB {
    private String b1;
    private com.autumn.learn.spring.domain.AA aa;
    private CC cc;
}
