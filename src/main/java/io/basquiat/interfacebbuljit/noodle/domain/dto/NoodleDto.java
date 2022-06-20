package io.basquiat.interfacebbuljit.noodle.domain.dto;

import io.basquiat.interfacebbuljit.noodle.domain.vo.Topping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 면 정보를 담는 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoodleDto {

    private String noodleName;

    private Topping topping;

}
