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
 * 파스타 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service("pasta")
@RequiredArgsConstructor
public class PastaService implements MenuFetcher {

    @Override
    public RecipeFetcher<Topping, ResponseResult<NoodleDto>> cooking() {
        return topping -> responseResult(NoodleDto.builder()
                                                  .noodleName(NoodleType.PASTA.getName())
                                                  .topping(topping)
                                                  .build()
                                        );
    }

}
