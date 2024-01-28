package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // 구현체 선택
    // OCP, DIP 원칙 위배 : 추상화에도 의존하고, 구현에도 의존한다.
    private final MemberRepository memberRepository = new MemoryMemberRepository();

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
