package com.autumn.learn.mvc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yl
 * @since 2022-11-12 16:40
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResultMessage<T> {
    private int codeStatus;
    private String msg;
    private T result;
}
