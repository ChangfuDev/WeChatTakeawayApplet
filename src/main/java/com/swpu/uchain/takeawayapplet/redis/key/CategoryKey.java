package com.swpu.uchain.takeawayapplet.redis.key;

/**
 * @ClassName CategoryKey
 * @Author hobo
 * @Date 19-3-3 下午3:51
 * @Description
 **/
public class CategoryKey extends BasePrefix {
    public CategoryKey(String prefix) {
        super(prefix);
    }

    public CategoryKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static CategoryKey categoryKey = new CategoryKey(350, "categoryType");
}
