package com.winsigns.investment.inventoryService.service;

public enum CapitalOperatorType {
    /**
     * <pre>
     *从资金封闭圈外转入到外部资金账户
     * </pre>
     *
     * <code>IN = 0;</code>
     */
    IN,
    /**
     * <pre>
     *从外部资金账户转出到资金封闭圈外
     * </pre>
     *
     * <code>OUT = 1;</code>
     */
    OUT,
    /**
     * <pre>
     *从A外部资金账户调拨入到B外部资金账户
     * </pre>
     *
     * <code>ALLOCATE = 2;</code>
     */
    ALLOCATE,
    /**
     * <pre>
     *从外部资金账户分配入到产品账户
     * </pre>
     *
     * <code>ASSIGN_IN = 4;</code>
     */
    ASSIGN_IN,
    /**
     * <pre>
     *从产品账户归还到外部资金账户
     * </pre>
     *
     * <code>ASSIGN_OUT = 5;</code>
     */
    ASSIGN_OUT,
    /**
     * <pre>
     *产品账户之间的让渡
     * </pre>
     *
     * <code>TRANSFER = 6;</code>
     */
    TRANSFER
}
