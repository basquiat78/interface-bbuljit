package io.basquiat.interfacebbuljit.noodle.domain;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

/**
 * Rest API response 정보를 담은 객체
 */
@EqualsAndHashCode
@RequiredArgsConstructor
public class ResponseResult<T> {

    private final T body;

    /**
     * ResponseResult를 생성하는 메소드
     * @param result
     * @param <T>
     * @return ResponseResult<T>
     */
    public static <T> ResponseResult<T> responseResult(T result) {
        return new ResponseResult<>(result);
    }

}
