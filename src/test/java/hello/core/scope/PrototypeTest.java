package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeTest {

    @Test
    void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototype1");
        PrototypeBean bean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototype2");
        PrototypeBean bean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototype1 = " + bean1 + ", prototype2 = " + bean2);
        Assertions.assertThat(bean1).isNotSameAs(bean2);

        bean1.destroy();
        bean2.destroy();

        ac.close();
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("SingletonBean.init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("SingletonBean.destroy");
        }
    }
}
