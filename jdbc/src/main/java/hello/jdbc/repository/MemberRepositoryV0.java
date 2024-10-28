package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.NoSuchElementException;

/*
    JDBC - DriverManager 사용
 */
@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);

            // SQL에 전달할 파라미터 바인딩
            // SQL injection을 예방하기 위해 pstmt를 통한 파라미터 바인딩을 사용해야함
            // 사용자 입력이 sql문이 아니라 단순 데이터로 취급되게 할 수 있다.
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());

            // DB에 쿼리 실행
            int count = pstmt.executeUpdate(); // 영향받은 row 수 반환

            return member;
        } catch (SQLException e) {
            log.error("DB error", e);
            throw e;
        } finally {
            // 외부 리소스를 쓰는 것이기 때문에 닫아줘야함
            // 중간에 Exception이 터지면 다음 것이 안닫힘
            // 닫는 것이 항상 호출되는 것이 보장되므로 finally에서 리소스 정리한다.
            // 리소스 정리는 항상 역순으로 한다.
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, memberId);

            rs = ps.executeQuery(); // 결과를 담는다.

            if (rs.next()) { // rs는 처음에는 아무것도 안가리키기 때문에 next()를 한 번은 호출해야한다.
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("Member Not Found - memberID = " + memberId);
            }
        } catch (SQLException e) {
            log.error("DB error", e);
            throw e;
        } finally {
            close(con, ps, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, money);
            ps.setString(2, memberId);

            int resultSize = ps.executeUpdate();
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.info("DB error", e);
            throw e;
        } finally {
            close(con, ps, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, memberId);
            ps.executeUpdate();
        } catch (SQLException e) {
            log.error("DB error: ", e);
            throw e;
        } finally {
            close(con, ps, null);
        }
    }

    private void close(Connection con, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("Close error", e);
            }
        }

        if (stmt != null) {
           try {
               stmt.close();
           } catch (SQLException e) {
               log.info("Close error", e);
           }
       }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("Close error", e);
            }
        }
    }
    private Connection getConnection() {
        return DBConnectionUtil.getConnection();
    }
}
