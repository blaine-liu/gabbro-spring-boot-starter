package io.github.aliothliu.gabbro.jsend;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.util.Assert;

import java.util.StringJoiner;

import static io.github.aliothliu.gabbro.jsend.JSendStatus.*;

/**
 * JSend 对象
 * 用于生成符合JSend规范的对象
 *
 * @author liubin
 */
@JsonSerialize(using = JSendSerializer.class)
public class JSend {

    private final JSendStatus status;
    private final Long code;
    private final String message;
    private final Object data;

    /**
     * JSend构造函数
     *
     * @param status  响应状态
     * @param code    响应状态码
     * @param message 错误消息文本
     * @param data    响应内容
     */
    public JSend(JSendStatus status, Long code, String message, Object data) {
        Assert.notNull(status, "JSend status should not be null");

        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 新建成功响应的JSend对象
     *
     * @param data 响应内容
     * @return JSend
     * @see JSendStatus#success
     */
    public static JSend success(Object data) {
        return new JSend(success, 200L, null, data);
    }

    /**
     * 新建成功响应的JSend对象
     *
     * @return JSend
     * @see JSendStatus#success
     */
    public static JSend success() {
        return success(null);
    }

    /**
     * 新建错误响应的JSend对象
     *
     * @param code    响应状态码
     * @param message 错误消息
     * @param data    响应内容
     * @return JSend
     * @see JSendStatus#error
     */
    public static JSend error(Long code, String message, Object data) {
        return new JSend(error, code, message, data);
    }

    /**
     * 新建错误响应的JSend对象
     *
     * @param message 错误消息
     * @return JSend
     * @see JSendStatus#error
     */
    public static JSend error(String message) {
        return error(null, message, null);
    }

    /**
     * 新建失败响应的JSend对象
     *
     * @param data 响应内容
     * @return JSend
     * @see JSendStatus#fail
     */
    public static JSend fail(Object data) {
        return new JSend(fail, null, null, data);
    }

    /**
     * 新建失败响应的JSend对象
     *
     * @param code    响应代码
     * @param message 响应消息
     * @param data    响应内容
     * @return JSend
     * @see JSendStatus#fail
     */
    public static JSend fail(Long code, String message, Object data) {
        return new JSend(fail, code, message, data);
    }

    public JSendStatus getStatus() {
        return status;
    }

    public Long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    /**
     * toString
     *
     * @return String
     */
    @Override
    public String toString() {
        return new StringJoiner(", ", JSend.class.getSimpleName() + "[", "]")
                .add("status=" + status)
                .add("code=" + code)
                .add("message='" + message + "'")
                .add("data='" + data + "'")
                .toString();
    }

    public static class Fields {
        public static String status = "status";
        public static String code = "code";
        public static String message = "message";
        public static String data = "data";
    }

}