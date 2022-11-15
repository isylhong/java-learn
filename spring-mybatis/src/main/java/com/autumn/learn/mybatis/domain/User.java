package com.autumn.learn.mybatis.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yl
 * @since 2022-11-14 22:17
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer id;
    private String userNumber;
    private String username;

    public User(String userNumber) {
        this(userNumber,null);
    }

    public User(String userNumber, String username) {
        this.userNumber = userNumber;
        this.username = username;
    }
}