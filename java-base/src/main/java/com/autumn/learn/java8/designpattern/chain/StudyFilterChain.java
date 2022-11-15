package com.autumn.learn.java8.designpattern.chain;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author yl
 * @since 2022-11-15 19:49
 */
public class StudyFilterChain implements FilterChain {
    public int pos = 0;
    public int count = 0;
    List<Filter> filterList = new ArrayList<>();
    private Study study;

    public StudyFilterChain() {

    }

    public StudyFilterChain(Study study) {
        this.study = study;
    }

    public StudyFilterChain(List<Filter> filterList, Study study) {
        this.filterList = filterList;
        this.count = filterList.size();
        this.study = study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public boolean addFilter(Filter filter) {
        boolean isAdd = this.filterList.add(filter);
        if (isAdd){
            this.count++;
        }
        return isAdd;
    }

    @Override
    public void doFilter(String arg0, String arg1) {
        int posNow;
        if ((posNow = this.pos++) >= this.count) {
            this.study.study();
            return;
        }
        Filter filter = filterList.get(posNow);
        //
        filter.doFilter(arg0, arg1, this);
    }
}
