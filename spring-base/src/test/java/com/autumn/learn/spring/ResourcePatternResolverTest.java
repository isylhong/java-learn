package com.autumn.learn.spring;

import org.junit.Test;
import org.springframework.util.AntPathMatcher;

/**
 *
 * @author yl
 * @since 2022-11-14 23:19
 */
public class ResourcePatternResolverTest {

    @Test
    public void test01() {
        System.out.println(determineRootDir("classpath*:a/b/c*/d/e.xml"));
    }

    protected String determineRootDir(String location) {
        int prefixEnd = location.indexOf(':') + 1;
        int rootDirEnd = location.length();
        while (rootDirEnd > prefixEnd && new AntPathMatcher().isPattern(location.substring(prefixEnd, rootDirEnd))) {
            rootDirEnd = location.lastIndexOf('/', rootDirEnd - 2) + 1;
        }
        if (rootDirEnd == 0) {
            rootDirEnd = prefixEnd;
        }
        return location.substring(0, rootDirEnd);
    }
}
