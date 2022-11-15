package com.autumn.learn.java8.designpattern.chain;

import org.junit.Test;

/**
 * 责任链模式
 *
 * @author yl
 * @since 2022-11-15 19:47
 */
public class ChainTest {
    @Test
    public void testResponsibilityChainAdvance() {
        Study study = new Study();
        Filter washFaceFilter = new WashFaceFilter();
        Filter washHairFilter = new WashHairFilter();
        Filter haveBreakfastFilter = new HaveBreakfastFilter();

        FilterChain filterChain = new StudyFilterChain(study);
        filterChain.addFilter(washFaceFilter);
        filterChain.addFilter(washHairFilter);
        filterChain.addFilter(haveBreakfastFilter);

        filterChain.doFilter("arg0", "arg1");
    }
}
