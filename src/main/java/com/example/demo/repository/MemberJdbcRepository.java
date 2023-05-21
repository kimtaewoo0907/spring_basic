package com.example.demo.repository;

import com.example.demo.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberJdbcRepository {

    // DataSource는 JDBC에서 DB 연결 드라이버
    @Autowired
    private DataSource dataSource;

    public void save(Member member) throws SQLException {
        String sql = "insert into member(name, email, password) values(?,?,?)";
        // dataSource객체 안에 DB정보를 담고 있고, 해당 DB정보를 통해 connection을 맺는다
        Connection con = dataSource.getConnection();
        // con객체에 작성한 쿼리문을 세팅하여 ps객체에 할당
        // ps: DB정보 + 쿼리문
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,member.getName());
        ps.setString(2,member.getEmail());
        ps.setString(3,member.getPassword());
        // insert또는 update는 executeUpdate 메서드 사용
        // select는 executeQeury 메서드 사용
        ps.executeUpdate();
    }

    public Member findById(Long id) throws SQLException {
        String sql = "select * from member where id = ?";
        Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1,id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Member member = new Member();
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            member.setPassword(rs.getString("password"));
            return member;
        }
        return null;
    }

        public List<Member> findAll() throws SQLException {
        String sql = "select * from member";
        Connection con = dataSource.getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Member> memberList = new ArrayList<>();
        while(rs.next()) {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setEmail(rs.getString("email"));
            memberList.add(member);
        }
            return memberList;
        }
}
