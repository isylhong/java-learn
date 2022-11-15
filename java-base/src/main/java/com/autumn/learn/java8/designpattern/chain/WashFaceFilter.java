package com.autumn.learn.java8.designpattern.chain;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:48
 */
public class WashFaceFilter implements Filter {

    @Override
    public void doFilter(String arg0, String arg1, FilterChain filterChain) {
        //
        System.out.println("洗完脸");

        filterChain.doFilter(arg0,arg1);
    }
}