package io.basquiat.interfacebbuljit.noodle.web;

import io.basquiat.interfacebbuljit.noodle.code.NoodleType;
import io.basquiat.interfacebbuljit.noodle.domain.ResponseResult;
import io.basquiat.interfacebbuljit.noodle.domain.dto.NoodleDto;
import io.basquiat.interfacebbuljit.noodle.domain.vo.Topping;
import io.basquiat.interfacebbuljit.noodle.service.NoodleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * noodle Rest Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/noodle")
public class NoodleRestController {

    private final NoodleService noodleService;

    /**
     * 면류를 만들어서 가져온다.
     * @return ResponseResult<NoodleDto>
     */
    @PostMapping("/{noodleType}")
    public ResponseResult<NoodleDto> noodle(@PathVariable("noodleType") String noodleType, @RequestBody Topping topping) {
        return noodleService.routeNoodleRecipe(NoodleType.of(noodleType), topping);
    }

}
