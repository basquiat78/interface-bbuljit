package io.basquiat.interfacebbuljit.noodle.code;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 면종류 enum type
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public enum NoodleType {

    NAENGMYEON("naengmyeon", "냉면"),

    LAMYEON("lamyeon", "라면"),

    PASTA("pasta", "파스타"),

    VIETNAMNOODLE("vietnamnoodle", "베트남 쌀국수"),

    NOT_FOUND("notFound", "없는 메뉴입니다.");

    @Getter
    private String type;

    @Getter
    private String name;

    /**
     * path variable로부터 넘어온 type값으로 Enum를 반환한다.
     * 없다면 NOT_FOUND 반환
     * @param noodle
     * @return NoodleType
     */
    public static NoodleType of(String noodle) {
        return Arrays.stream(NoodleType.values())
                     .filter( noodleType -> noodleType.getType().equalsIgnoreCase(noodle) )
                     .findAny()
                     .orElse(NoodleType.NOT_FOUND);
    }

}
