package com.autumn.learn.java8.designpattern.adapter.refactor;

import com.autumn.learn.java8.designpattern.adapter.oldservice.PassportService;

/**
 *
 * @author yl
 * @since 2022-11-15 19:37
 */
public abstract class AbstractAdapter extends PassportService implements ILoginAdapter {
}