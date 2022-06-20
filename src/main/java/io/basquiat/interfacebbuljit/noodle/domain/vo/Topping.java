package io.basquiat.interfacebbuljit.noodle.domain.vo;

import io.basquiat.interfacebbuljit.noodle.code.AddOrNot;
import lombok.Value;

/**
 * 토핑 유무
 * 고객의 토핑 요청 사항을 바꿀 수 없게 immutable 하게 만든다.
 */
@Value
public class Topping {

    private AddOrNot egg;

    private AddOrNot rice;

    private AddOrNot garlicFlake;

    private AddOrNot peanutButter;

    private AddOrNot spicySource;

}
