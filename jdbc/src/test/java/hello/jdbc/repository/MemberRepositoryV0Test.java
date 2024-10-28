package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repositoryV0 = new MemberRepositoryV0();

    @Test // 테스트를 반복실행할 수 있는 것이 중요하다.
    void crud() throws SQLException {
        // save
        Member member = new Member("member4", 10000);
        repositoryV0.save(member);

        // findById
        Member findMember = repositoryV0.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        // 인스턴스는 다른데, equals 했을 때는 같다고 나온다.
        // Data lombok이 값이 같으면 같다고 판단해주는 equals를 만들어준다.
        Assertions.assertThat(findMember).isEqualTo(member);

        // update: money: 10000->20000
        repositoryV0.update(member.getMemberId(), 20000);
        Member updateMember = repositoryV0.findById(member.getMemberId());
        Assertions.assertThat(updateMember.getMoney()).isEqualTo(20000);

        // delete
        // 삭제된 건 어떻게 검증하지?
        // 찾는데 없을 때 발생하는 예외가 발생하는지 검증하자.
        repositoryV0.delete(member.getMemberId());
        Assertions.assertThatThrownBy(() -> repositoryV0.findById(member.getMemberId()))
                        .isInstanceOf(NoSuchElementException.class);
    }

}