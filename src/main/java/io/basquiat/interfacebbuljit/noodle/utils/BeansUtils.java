package io.basquiat.interfacebbuljit.noodle.utils;

import io.basquiat.interfacebbuljit.noodle.provider.ApplicationContextProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;

/**
 * beansUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeansUtils {

    /**
     * 외부에서 캐스팅하지 말고 내부적으로 캐스팅하게 제너릭으로 만든다.
     * @param <T>
     * @param beanId
     * @param clazz
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
