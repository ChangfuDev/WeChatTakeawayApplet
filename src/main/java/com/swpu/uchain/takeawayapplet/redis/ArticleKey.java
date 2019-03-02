package com.swpu.uchain.takeawayapplet.redis;

/**
 * @Description
 * @Author cby
 * @Date 19-1-20
 **/
public class ArticleKey extends BasePrefix {

    public ArticleKey(int experiSeconds, String prefix) {
        super(experiSeconds, prefix);
    }

    public static ArticleKey articleKey = new ArticleKey(400, "title");
}
