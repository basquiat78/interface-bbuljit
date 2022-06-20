package io.basquiat.interfacebbuljit.noodle.provider;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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

    /**
     * 내부에서만 ApplicationContext에 접근가능하게
     */
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
