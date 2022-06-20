package io.basquiat.interfacebbuljit.noodle.fetcher;

import io.basquiat.interfacebbuljit.noodle.domain.ResponseResult;
import io.basquiat.interfacebbuljit.noodle.domain.dto.NoodleDto;
import io.basquiat.interfacebbuljit.noodle.domain.vo.Topping;

/**
 * menu fetcher
 */
public interface MenuFetcher {

    RecipeFetcher<Topping, ResponseResult<NoodleDto>> cooking();

}
