package com.idiomchain.common;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String message;
    private T data;

    /**
     * 成功响应，不带数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(200, "Success", null);
    }

    /**
     * 成功响应，带数据
     * @param data 响应数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "Success", data);
    }

    /**
     * 成功响应，带自定义消息和数据
     * @param message 响应消息
     * @param data 响应数据
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(200, message, data);
    }

    /**
     * 失败响应，带错误代码和消息
     * @param code 错误代码
     * @param message 错误消息
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }

    /**
     * 失败响应，带错误消息
     * @param message 错误消息
     * @return ApiResponse
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(400, message, null);
    }
}