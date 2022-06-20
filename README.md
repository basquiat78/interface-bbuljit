# interface-bbuljit

뻘짓하는 것은 즐겁다.

반복되는 패턴으로 늘어만 가는 컨트롤러의 엔드포인트와 서비스의 로직을 나름대로의 SRP원칙에 의거해 나누는

별 의미 없는 내용을 다루고 있다.

누군가에게는 도움이 되기를...

## 면요리를 요청하는 API를 만들자.

NoodleRestController.java

```
/**
 * noodle Rest Controller
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/noodle")
public class NoodleRestController {

    /**
     * 냉면을 만들어서 가져온다.
     * @return String
     */
    @PostMapping("/naengmyeon")
    public String naengmyeon() {
        return "맛있는 냉면";
    }

    /**
     * 라면을 만들어서 가져온다.
     * @return ResponseResult
     */
    @PostMapping("/lamyeon")
    public String lamyeon() {
        return "맛있는 라면";
    }

}

```

다음과 같은 초 간단 api가 있다고 해보자.

단지 두개의 API가 존재한다. 사실 너무나 간단해서 더이상 할 말이 없는 API이다.

이제 레시피를 통해서 해당 면을 반환하는 서비스를 만들어 보자.

뭔가 그럴싸하게 Noodle이라는 객체에 정보를 담아서 반환하는 초간단 서비스이다.

Noodle.java
```
/**
 * 면 정보를 담는 객체
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoodleDto {

    private String noodleName;

}
```

응답 객체 ResponseResult도 만들어 보자.
```
/**
 * Rest API response 정보를 담은 객체
 */
@Data
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
```


NoodleService.java
```
/**
 * 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service
@RequiredArgsConstructor
public class NoodleService {

    /**
     * 냉면 레시피
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> cookingNaengmyeon() {
        return responseResult(NoodleDto.builder().noodleName("냉면").build());
    }

    /**
     * 라면 레시피
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> cookingLamyeon() {
        return responseResult(NoodleDto.builder().noodleName("라면").build());
    }

}
```

근데 이 집이 메뉴를 개발하기 시작하고 인기가 많아지면서 몇개의 API가 더 추가되는 상황을 상상해 보자.

```
/**
 * noodle Rest Controller
 */
@RestController
@RequestMapping("/api/noodle")
@RequiredArgsConstructor
public class NoodleRestController {

    private final NoodleService noodleService;

    /**
     * 냉면을 만들어서 가져온다.
     * @return ResponseResult<NoodleDto>
     */
    @PostMapping("/naengmyeon")
    public ResponseResult<NoodleDto> naengmyeon() {
        return noodleService.cookingNaengmyeon();
    }

    /**
     * 라면을 만들어서 가져온다.
     * @return ResponseResult<NoodleDto>
     */
    @PostMapping("/lamyeon")
    public ResponseResult<NoodleDto> lamyeon() {
        return noodleService.cookingLamyeon();
    }

    /**
     * 파스타를 만들어서 가져온다.
     * @return ResponseResult<NoodleDto>
     */
    @PostMapping("/pasta")
    public ResponseResult<NoodleDto> pasta() {
        return noodleService.cookingPasta();
    }

    /**
     * 베트남 쌀국수를 만들어서 가져온다.
     * @return ResponseResult<NoodleDto>
     */
    @PostMapping("/vietnamnoodle")
    public ResponseResult<NoodleDto> vietnamnoodle() {
        return noodleService.cookingVietnamnoodle();
    }

}
```

그렇다면 서비스쪽도 이렇게 바뀌겠지?
```
/**
 * 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service
@RequiredArgsConstructor
public class NoodleService {

    /**
     * 냉면 레시피
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> cookingNaengmyeon() {
        return responseResult(NoodleDto.builder().noodleName("냉면").build());
    }

    /**
     * 라면 레시피
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> cookingLamyeon() {
        return responseResult(NoodleDto.builder().noodleName("라면").build());
    }

    /**
     * 파스타 레시피
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> cookingPasta() {
        return responseResult(NoodleDto.builder().noodleName("파스타").build());
    }

    /**
     * 베트남 쌀국수 레시피
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> cookingVietnamnoodle() {
        return responseResult(NoodleDto.builder().noodleName("베트남 쌀국수").build());
    }

}
```

문득 이런 생각이 들것이다.

아니 이럴거면 그냥 path variable을 이용하지?

~~어 그래 화내지~~

```
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
     * @return ResponseResult
     */
    @PostMapping("/{noodleType}")
    public ResponseResult<NoodleDto> naengmyeon(@PathVariable("noodleType") String noodleType) {
        if("naengmyeon".equals(noodleType)) {
            return noodleService.cookingNaengmyeon(noodleType);    
        } else if("메뉴들". equals(noodleType)) {
            .
            .
            .
        }
        return responseResult(NoodleDto.builder().noodleName("없는 메뉴입니다.").build());
    }

}
```
수많은 엔드포인트가 사라지고 굉장히 깔금하게 변했다.

하지만 컨트롤러쪽에 if문이 좀 지져분해 보인다.

차라리 서비스쪽으로 넘기고 서비스쪽에서 무언가를 해봐야겠다는 생각이 퍼득 들기 시작한다.

일단 심플하게 면종류를 enum으로 정의를 하고 만일 없는 메뉴가 넘어오게 된다면 없다는 정보를 보내주게 한번 만들어 보자.

```
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
     * @param type
     * @return NoodleType
     */
    public static NoodleType of(String type) {
        return Arrays.stream(NoodleType.values())
                     .filter( noodleType -> noodleType.getType().equalsIgnoreCase(type) )
                     .findAny()
                     .orElse(NoodleType.NOT_FOUND);
    }

}
```

하지만 위의 컨트롤러는 서비스와 맞물려 있기 때문에 서비스도 좀 수정해야 한다.

일단 무지성으로 만들어보면 다음과 같이 한번 만들어 볼 수 있다.

```
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
    public ResponseResult<NoodleDto> routeNoodleRecipe(NoodleType noodleType) {
        switch (noodleType) {
            case NAENGMYEON:
                return cookingNaengmyeon();
            case LAMYEON:
                return cookingLamyeon();
            case PASTA:
                return cookingPasta();
            case VIETNAMNOODLE:
                return cookingVietnamnoodle();
        }
        return responseResult(NoodleDto.builder().noodleName(noodleType.getNoodleName()).build());
    }

    /**
     * 냉면 레시피
     * @return ResponseResult<NoodleDto>
     */
    private ResponseResult<NoodleDto> cookingNaengmyeon() {
        return responseResult(NoodleDto.builder().noodleName("냉면").build());
    }

    /**
     * 라면 레시피
     * @return ResponseResult<NoodleDto>
     */
    private ResponseResult<NoodleDto> cookingLamyeon() {
        return responseResult(NoodleDto.builder().noodleName("라면").build());
    }

    /**
     * 파스타 레시피
     * @return ResponseResult<NoodleDto>
     */
    private ResponseResult<NoodleDto> cookingPasta() {
        return responseResult(NoodleDto.builder().noodleName("파스타").build());
    }

    /**
     * 베트남 쌀국수 레시피
     * @return ResponseResult<NoodleDto>
     */
    private ResponseResult<NoodleDto> cookingVietnamnoodle() {
        return responseResult(NoodleDto.builder().noodleName("베트남 쌀국수").build());
    }

}
```
if문을 사용해도 되고 enum을 받으니 switch로 분기를 태웠지만 뭔가 고개가 갸우뚱거려진다.

하지만 일단 작동은 하니까 그냥 넘어가자!


컨트롤러 부분은 다음과 같이 바꾼다.
```
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
    public ResponseResult<NoodleDto> noodle(@PathVariable("noodleType") String noodleType) {
        return noodleService.routeNoodleRecipe(NoodleType.of(noodleType));
    }

}
```
하지만 뭔가 컨트롤러 쪽은 깔끔하게 변경이 되었는데 서비스쪽이 참 맘에 걸린다.

메뉴가 추가되면 enum을 정의하는 것은 그렇다치고 길어지는 switch문이 걸린다.

switch문이 늘어나는 것도 어쩔 수 없다고 그렇다고 치자. 하지만 서비스 내부에 각 메뉴별 레시피를 추가적으로 정의하는 것이 뭔가 번거롭다.

많아지면 많아질수록 가독성도 떨어진다.

어짜피 지금이야 간단하지만 DB와 연계도 하고 무언가를 확장할때를 대비해서 SRP에 입각해 메뉴별로 레시피를 담당하는 서비스를 만들고 그것을 호출하게 하면 뭔가
그럴싸하게 보인다.

그리고 각각의 서비스 내에서 해당 레시피에 대한 것만 담당하게 한다면 참 좋을텐데라는 생각까지 하게 된다.

예를 들면
```
/**
 * 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service
@RequiredArgsConstructor
public class NoodleService {

    private final NaengmyeonService naengmyeon;
    private final LamyeonService lamyeon;
    private final PastaService pasta;
    private final VietnamnoodleService vietnamnoodle;


    /**
     * 공통 레시피를 받는 메소드
     * @param noodleType
     * @return ResponseResult
     */
    public ResponseResult<NoodleDto> routeNoodleRecipe(NoodleType noodleType) {
        switch (noodleType) {
            case NAENGMYEON:
                return naengmyeon.recipe();
            case LAMYEON:
                return lamyeon.recipe();
            case PASTA:
                return pasta.recipe();
            case VIETNAMNOODLE:
                return vietnamnoodle.recipe();
        }
        return responseResult(NoodleDto.builder().noodleName(noodleType.getNoodleName()).build());
    }
}

```

스위치 문이 길어지더라도 추가되는 서비스에 대한 레시피를 담당하는 서비스만 잘 정의하면 무난하게 갈 수 있다는 것이다.

하지만 추가되는 서비스만큼 생성자로 주입하는 서비스의 갯수가 맘에 걸리기 시작한다.

사실 잘 굴러만 간다면 된다는 마인드라면 별 문제가 없어보인다.

'우리집은 곧 면요리 맛집이 될거고 메뉴도 많이 늘어날텐데 이게 걸리네?'

좀더 좋은 방법이 없을까 고민해 보지만 지금 딱히 생각나는 것은 인터페이스를 활용하는 것이다.

그리고 Spring의 ApplicationContext를 통해 서비스를 가져와서 사용해 볼까 한다.

한번 해보는 거지. 또한 이런 경우를 마주할 때를 대비한 일종의 뻘짓이다.

일단 RecipeFetcher라는 인터페이스를 한번 만들어 보자.

현재의 구조로 볼때는 인자값이 없고 T객체를 반환하는 구조이기 때문에 functional interface중 이에 해당하는 Supplier를 적용해서 간단하게 만든다.

```
/**
 * Supplier
 * @param <T>
 */
public interface RecipeFetcher<T> {

    T get();
    
}
```

그리고 MenuFetcher라는 녀석을 하나 정의해보자.

```
/**
 * menu fetcher
 */
public interface MenuFetcher {

    RecipeFetcher cooking();

}
```
반환타입이 RecipeFetcher라는 것에 주목하자.

이제부터 이것을 구현한 메뉴별 레시피를 정의한 서비스를 만들 생각이다.

```
/**
 * 냉면 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service("naengmyeon")
@RequiredArgsConstructor
public class NaengmyeonService implements MenuFetcher {

    @Override
    public RecipeFetcher cooking() {
        return () -> responseResult(NoodleDto.builder().noodleName(NoodleType.NAENGMYEON.getNoodleName()).build());
    }

}
```
스프링에에서 스프링 컨테이너인 ApplicationContext를 이용해 빈을 가져올 수 있기 때문에 NoodleType에 정의된 type명으로 빈을 찾을 수 있도록 서비스 네임을 지정한다.

최종 NoodleService는 다음과 같이 변경될 것이다.

```
/**
 * 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service
@RequiredArgsConstructor
public class NoodleService {

    private final ApplicationContext context;

    /**
     * 공통 레시피를 받는 메소드
     * @param noodleType
     * @return ResponseResult<NoodleDto>
     */
    public ResponseResult<NoodleDto> routeNoodleRecipe(NoodleType noodleType) {
        MenuFetcher fetcher = null;
        try {
            fetcher = (MenuFetcher) context.getBean(noodleType.getNoodleType());
        } catch(Exception e) {
            // fetcher를 통해 빈을 가져오지 못하면 NOT_FOUND를 리턴해 주자
            return responseResult(NoodleDto.builder().noodleName(NoodleType.NOT_FOUND.getNoodleName()).build());
        }
        return fetcher.cooking().get();
    }

}
```

이렇게 하면 메뉴가 추가될 때 단지 MenuFetcher를 구현한 해당 메뉴의 레시피를 작성만 하면 된다.

단점이라고 한다면 이런 서비스 클래스가 늘어나겠지만 유지보수에 뛰어날 수 있다.

하지만 뭔가 좀 아쉽다.  주문을 받을 때 우리는 면에 올라올 토핑을 추가할 수 있기 때문에 관련 정보를 받아서 처리해 보고 싶은 욕망이 생긴다.

Topping이라는 Vo를 통해서 레시피를 만들때 마지막으로 올라올 토핑 정보를 받아서 응답 객체에 이것을 표현해 보자.

일단 번거롭지만 토핑 유무를 따지는 enum을 만들고
```
/**
 * 추가하거나 안한거나
 */
public enum AddOrNot {
    ADD,
    NOT
}


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

```

물론 AddOrNot enum class는 Topping클래스 내부에 작성할 수 있지만 혹시 또 다른 곳에서도 사용할 수 있다는 가정하에 분리를 한다.

간단하게 달걀, 떡, 마늘 후레이크, 땅콩 버터, 핫소스 정도의 토핑만 생성해 보자.

이제는 컨트롤러에서 이것을 받아보자.

Supplier는 인자값이 없고 어떤 객체 T를 리턴받지만 이제는 하나의 인자값을 받고 객체 T를 리턴받아야 한다.

따라서 RecipeFetcher는 다음과 같이 변경한다.

이 때 우리가 선택할 수 있는 것은 Function<T, R>이다.

```
/**
 * Function<T, R>
 * @param <T>
 * @param <R>
 */
public interface RecipeFetcher<T, R> {

    R apply(T t);

}


/**
 * menu fetcher
 */
public interface MenuFetcher {

    RecipeFetcher<Topping, ResponseResult> cooking();

}

```

MenuFetcher 역시 바껴야 한다. T를 인자로 받고 R로 반환하기 때문에 각각의 서비스는 다음과 같이 바꾸자.

그전에 NoodleDto에서는 이 토핑을 받게 만들자.


```
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

/**
 * 라면 레시피를 통해서 면을 만들고 반환을 한다.
 *
 */
@Service("lamyeon")
@RequiredArgsConstructor
public class LamyeonService implements MenuFetcher {

    @Override
    public RecipeFetcher<Topping, ResponseResult<NoodleDto>> cooking() {
        return topping -> responseResult(NoodleDto.builder()
                                                  .noodleName(NoodleType.LAMYEON.getName())
                                                  .topping(topping)
                                                  .build()
                                        );
    }

}
```

만일 2개의 인자를 받아야 한다면 대부분의 functional interface에서 제공하는 compose, andThen을 이용해 연쇄적으로 받아서 처리할 수 있게 조합을 할 수 있다.

여기서 Function이나 Supplier를 직접 쓰지 굳이 RecipeFetcher를 만든 이유가 궁금할 수도 있는 분이 있으실텐데 그것은 이름을 주기 위해서이다.

정확히는 이것이 무엇을 하는 녀석인지 이름을 주어 가독성을 높여주는게 주 목적이다.

또한 지금 직접적으로 ApplicationContext를 주입해 getBean()을 호출했지만 다음과 같이 고전적인 방식으로 BeansUtils를 만들어서 재활용할 수 있도록 사용해보자.

ApplicationContextAware를 구현한 ApplicationContextProvider를 통해서 스프링 컨테이너를 제공하는 제공자를 만든다.

```
/**
 * ApplicationContextAware 을 이용해서 ApplicationContext 를 제공하는 제공자를 만든다.
 * 
 * 인스턴스가 조작되는 것을 private static final 로 정의된 inner class 통해 감싸서 제공한다.
 * 
 */
@Component
public class ApplicationContextProvider implements ApplicationContextAware {

    private static class ApplicationContextHolder {

        private static final InnerPrivateResource INNER_PRIVATE_RESOURCE = new InnerPrivateResource();

        private ApplicationContextHolder() {
            super();
        }

    }

    private static final class InnerPrivateResource {

        private ApplicationContext context;

        private InnerPrivateResource() {
            super();
        }

        private void setContext(ApplicationContext context) {
            this.context = context;
        }

    }

    public static ApplicationContext getApplicationContext() {
        return ApplicationContextHolder.INNER_PRIVATE_RESOURCE.context;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) {
        ApplicationContextHolder.INNER_PRIVATE_RESOURCE.setContext(context);
    }

}

/**
 * beansUtils
 */
public class BeansUtils {

    /**
     * 외부에서 캐스팅하지 말고 내부적으로 캐스팅하게 제너릭으로 만든다.
     * @param beanId
     * @param clazz
     * @param <T>
     * @return T
     */
    public static <T> T getBean(String beanId, Class<T> clazz) {
        ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
        if(applicationContext == null) {
            throw new NullPointerException("Not Initialize Spring Container!");
        }
        return (T)applicationContext.getBean(beanId);
    }

}
```

그리고 최종적으로

```
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
            // getBean에서 빈을 가져오지 못하면 에러가 발생할 것이다. 없는 메뉴가 넘어온 것이기에 NOT_FOUND를 리턴해 주자
            return responseResult(NoodleDto.builder().noodleName(NoodleType.NOT_FOUND.getName()).build());
        }
        return fetcher.cooking().apply(topping);
    }

}
```

사실 이런 이상적인 상황을 많이 만날지는 미지수이다.

하지만 이런 상황이 발생한다면 한번쯤은 시도해 볼 만한 뻘짓이다.