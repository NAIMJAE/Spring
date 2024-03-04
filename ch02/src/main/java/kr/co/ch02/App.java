package kr.co.ch02;

import kr.co.ch02.config.AppConfig;
import kr.co.ch02.sub1.Greeting;
import kr.co.ch02.sub1.Hello;
import kr.co.ch02.sub1.Welcome;
import kr.co.ch02.sub2.Computer;
import kr.co.ch02.sub2.Cpu;
import kr.co.ch02.sub2.Hdd;
import kr.co.ch02.sub2.Ram;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 날짜 : 2024/03/04
 * 이름 : 박임재
 * 내용 : Spring IoC / DI 실습하기
 *
 * @Configuration
 *  - 애플리케이션을 구성하는 빈을 등록하기 위한 설정 클래스
 *  - XML, 설정파일 대신 Java 클래스를 사용해 스프링 컨테이너 설정
 *
 * @Bean
 *  - 컨테이너에 등록하기 위한 빈 설정
 *  - 사용자 정의 클래스, 외부 라이브러리 빈 등록
 *
 * @ComponentScan
 *  - basePackages로 시작하는 패키지내의 빈을 스캔해서 컨테이너에 등록
 *  - 스캔 대상 컴포넌트는 @Component 선언
 *
 * @Component
 *  - 스캐닝으로 컨테이너에 등록할 대상 컴포넌트 설정
 *  - @Controller, @Service, @Repository로 파생
 *  
 * @Autowired
 *  - 컨테이너의 빈을 주입
 *  - 이름 또는 클래스 타입으로 매칭된 빈을 주입
 */
public class App 
{
    public static void main( String[] args )
    {
        // 스프링 컨테이너 생성
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 빈 가져오기ㅠ
        Hello hello = context.getBean(Hello.class);
        hello.show();

        Welcome welcome = (Welcome)context.getBean("welcome");
        welcome.show();

        Greeting greeting = (Greeting)context.getBean("greeting");
        greeting.show();

        Computer com = (Computer) context.getBean("com");
        com.show();
    }
}
