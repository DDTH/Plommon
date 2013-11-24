package com.github.ddth.plommon.bo;

/**
 * Used to pass a raw expression to a SQL statement.
 * 
 * @author Thanh Nguyen <btnguyen2k@gmail.com>
 * @since 0.4.1
 */
public class ParamExpression {

    private String expr;

    public ParamExpression(String expr) {
        this.expr = expr;
    }

    public String getExpression() {
        return expr;
    }
}
