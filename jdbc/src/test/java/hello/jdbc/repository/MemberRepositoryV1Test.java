package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repositoryV1;

    @BeforeEach
    void beforeEach() {
        // 기본 DriverManager = 항상 새로운 커넥션 획득
//        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
//        repositoryV1 = new MemberRepositoryV1(dataSource);

        // 커넥션 풀링
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        repositoryV1 = new MemberRepositoryV1(dataSource);

        // DriverManager -> Hikari로 변경해도 memberrepository 코드는 변경하지 않아도 됨
        // 이것이 datasource 인터페이스 (표준화)를 사용하는 이유!!!
        // 개발자는 편하게 갈아끼우기만 하자
    }

    @Test // 테스트를 반복실행할 수 있는 것이 중요하다.
    void crud() throws SQLException {
        // save
        Member member = new Member("member4", 10000);
        repositoryV1.save(member);

        // findById
        Member findMember = repositoryV1.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        // 인스턴스는 다른데, equals 했을 때는 같다고 나온다.
        // Data lombok이 값이 같으면 같다고 판단해주는 equals를 만들어준다.
        Assertions.assertThat(findMember).isEqualTo(member);

        // update: money: 10000->20000
        repositoryV1.update(member.getMemberId(), 20000);
        Member updateMember = repositoryV1.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        // delete
        // 삭제된 건 어떻게 검증하지?
        // 찾는데 없을 때 발생하는 예외가 발생하는지 검증하자.
        repositoryV1.delete(member.getMemberId());
        Assertions.assertThatThrownBy(() -> repositoryV1.findById(member.getMemberId()))
                        .isInstanceOf(NoSuchElementException.class);
    }

}