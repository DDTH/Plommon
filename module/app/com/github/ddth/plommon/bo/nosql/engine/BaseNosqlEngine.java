package com.github.ddth.plommon.bo.nosql.engine;

import java.nio.charset.Charset;

import com.github.ddth.plommon.bo.nosql.INosqlEngine;

/**
 * Abstract implementation of {@link INosqlEngine}.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.5.0
 */
public abstract class BaseNosqlEngine implements INosqlEngine {

    protected final static Charset CHARSET = Charset.forName("UTF_8");

    public BaseNosqlEngine init() {
        return this;
    }

    public void destroy() {
    }
}
