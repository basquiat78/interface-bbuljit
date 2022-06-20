package io.basquiat.interfacebbuljit.noodle.service;

import io.basquiat.interfacebbuljit.noodle.code.NoodleType;
import io.basquiat.interfacebbuljit.noodle.domain.ResponseResult;
import io.basquiat.interfacebbuljit.noodle.domain.dto.NoodleDto;
import io.basquiat.interfacebbuljit.noodle.domain.vo.Topping;
import io.basquiat.interfacebbuljit.noodle.fetcher.MenuFetcher;
import io.basquiat.interfacebbuljit.noodle.fetcher.RecipeFetcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static io.basquiat.interfacebbuljit.noodle.domain.ResponseResult.responseResult;

/**
 * 냉면 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service("naengmyeon")
@RequiredArgsConstructor
public class NaengmyeonService implements MenuFetcher {

    @Override
    public RecipeFetcher<Topping, ResponseResult<NoodleDto>> cooking() {
        return topping -> responseResult(NoodleDto.builder()
                                                  .noodleName(NoodleType.NAENGMYEON.getName())
                                                  .topping(topping)
                                                  .build()
                                        );
    }

}
