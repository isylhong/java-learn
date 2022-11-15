package com.autumn.learn.springmvc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author yl
 * @since 2022-11-12 16:38
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Integer userId;
    private String username;

    public static class Builder {
        private Integer userId;
        private String username;

        Builder() {
        }

        public Builder(Integer userId) {
            this.userId = userId;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public User build() {
            return new User(this.userId, this.username);
        }
    }
}
