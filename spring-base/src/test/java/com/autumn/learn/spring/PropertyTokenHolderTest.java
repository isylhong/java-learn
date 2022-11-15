package com.autumn.learn.spring;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.PropertyAccessor;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yl
 * @since 2022-11-14 23:18
 */
public class PropertyTokenHolderTest {
    public static final String PROPERTY_KEY_PREFIX="[";

    public static final String PROPERTY_KEY_SUFFIX="]";

    public static void main(String[] args) {
//        String propertyName = "users[2][1].name";
        String propertyName = "users[2][1].name";
        PropertyTokenHolderTest propertyTokenHolder = new PropertyTokenHolderTest().getPropertyNameTokens(propertyName);
        System.out.println(JSONObject.toJSON(propertyTokenHolder));
    }

    public String actualName; // 如果属性是一个数组/集合，则为该集合的名称，否则直接为属性名。如属性名为users[1].name，对应的canonicalName为users

    public String canonicalName; // 如果属性是一个数组/集合，数组/集合中的具体元素名称，否则直接为属性名。如属性名为users[1].name，对应的canonicalName为users[1]

    @Nullable
    public String[] keys;


    public PropertyTokenHolderTest() {
    }

    public PropertyTokenHolderTest(String name) {
        this.actualName = name;
        this.canonicalName = name;
    }


    /**
     * 给定一个属性名，返回一个PropertyTokenHolder对象
     * 如：
     * name    返回：{"actualName":"name","canonicalName":"name"}
     * user.name   返回：{"actualName":"user.name","canonicalName":"user.name"}
     * users[1].name    返回：{"actualName":"users","keys":["1"],"canonicalName":"users[1]"}
     *
     * @param propertyName
     * @return
     */
    private PropertyTokenHolderTest getPropertyNameTokens(String propertyName) {
        String actualName = null;
        List<String> keys = new ArrayList<>(2);
        int searchIndex = 0;
        while (searchIndex != -1) {
            int keyStart = propertyName.indexOf(PROPERTY_KEY_PREFIX, searchIndex);
            searchIndex = -1;
            if (keyStart != -1) {
                int keyEnd = getPropertyNameKeyEnd(propertyName, keyStart + PROPERTY_KEY_PREFIX.length());
                if (keyEnd != -1) {
                    if (actualName == null) {
                        actualName = propertyName.substring(0, keyStart);
                    }
                    String key = propertyName.substring(keyStart + PROPERTY_KEY_PREFIX.length(), keyEnd);
                    if (key.length() > 1 && (key.startsWith("'") && key.endsWith("'")) ||
                            (key.startsWith("\"") && key.endsWith("\""))) {
                        key = key.substring(1, key.length() - 1);
                    }
                    keys.add(key);
                    searchIndex = keyEnd + PROPERTY_KEY_SUFFIX.length();
                }
            }
        }
        PropertyTokenHolderTest tokens = new PropertyTokenHolderTest(actualName != null ? actualName :
                propertyName);
        if (!keys.isEmpty()) {
            tokens.canonicalName += PROPERTY_KEY_PREFIX +
                    StringUtils.collectionToDelimitedString(keys,
                            PROPERTY_KEY_SUFFIX + PROPERTY_KEY_PREFIX) +
                    PROPERTY_KEY_SUFFIX;
            tokens.keys = StringUtils.toStringArray(keys);
        }
        return tokens;
    }


    private int getPropertyNameKeyEnd(String propertyName, int startIndex) {
        int unclosedPrefixes = 0;
        int length = propertyName.length();
        for (int i = startIndex; i < length; i++) {
            switch (propertyName.charAt(i)) {
                case PropertyAccessor.PROPERTY_KEY_PREFIX_CHAR:
                    // The property name contains opening prefix(es)...
                    unclosedPrefixes++;
                    break;
                case PropertyAccessor.PROPERTY_KEY_SUFFIX_CHAR:
                    if (unclosedPrefixes == 0) {
                        // No unclosed prefix(es) in the property name (left) ->
                        // this is the suffix we are looking for.
                        return i;
                    } else {
                        // This suffix does not close the initial prefix but rather
                        // just one that occurred within the property name.
                        unclosedPrefixes--;
                    }
                    break;
            }
        }
        return -1;
    }
}
