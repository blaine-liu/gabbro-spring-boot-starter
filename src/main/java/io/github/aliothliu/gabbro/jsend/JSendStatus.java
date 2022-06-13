package io.github.aliothliu.gabbro.jsend;

/**
 * JSend 状态
 *
 * @author liubin
 */
public enum JSendStatus {

    /**
     * 响应成功状态
     */
    success,

    /**
     * 响应失败状态，一般指的是业务逻辑不匹配
     */
    fail,

    /**
     * 错误
     */
    error

}