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
public class AA {
    private int a1;
    private BB bb;

    @NoArgsConstructor
    @Getter
    @Setter
    class DD extends com.autumn.learn.spring.domain.AA {
        private CC cc;
    }
}
