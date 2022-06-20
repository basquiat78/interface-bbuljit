package io.basquiat.interfacebbuljit.noodle.service;

import io.basquiat.interfacebbuljit.noodle.code.NoodleType;
import io.basquiat.interfacebbuljit.noodle.domain.ResponseResult;
import io.basquiat.interfacebbuljit.noodle.domain.dto.NoodleDto;
import io.basquiat.interfacebbuljit.noodle.domain.vo.Topping;
import io.basquiat.interfacebbuljit.noodle.fetcher.MenuFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.basquiat.interfacebbuljit.noodle.domain.ResponseResult.responseResult;
import static io.basquiat.interfacebbuljit.noodle.utils.BeansUtils.getBean;

/**
 * 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service
@RequiredArgsConstructor
public class NoodleService {

    /**
     * 공통 레시피를 받는 메소드
     * @param noodleType
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> routeNoodleRecipe(NoodleType noodleType, Topping topping) {
        MenuFetcher fetcher;
        try {
            fetcher = getBean(noodleType.getType(), MenuFetcher.class);
        } catch(Exception e) {
            // fetcher를 통해 빈을 가져오지 못하면 NOT_FOUND를 리턴해 주자
            return responseResult(NoodleDto.builder().noodleName(NoodleType.NOT_FOUND.getName()).build());
        }
        return fetcher.cooking().apply(topping);
    }

}
