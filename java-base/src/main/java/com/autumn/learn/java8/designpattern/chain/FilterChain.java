package com.autumn.learn.java8.designpattern.chain;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:48
 */
public interface FilterChain {
    public void doFilter(String arg0, String arg1);

    public boolean addFilter(Filter filter);
}
