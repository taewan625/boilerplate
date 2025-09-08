package com.exporum.admin.core.response;


import lombok.Getter;

/**
 * ContentsResponse.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 8. 최초 작성
 */

@Getter
public class ContentsResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private ContentsResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ContentsResponse<T> success(String message, T data) {
        return new ContentsResponse<>(true, message, data);
    }
}