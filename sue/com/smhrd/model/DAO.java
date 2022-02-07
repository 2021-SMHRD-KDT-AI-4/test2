package com.smhrd.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAO {
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	// 회원가입할때 호출할 메서드
	public int join(String email, String pw, String tel, String address) {
		// try~catch : 예외처리(런타임오류)
		int cnt = 0;
		try {
			// 1. JDBC 드라이버 동적 로딩
			// ClassNotFoundException
			// 해결방법 1. 2
			Class.forName("oracle.jdbc.driver.OracleDriver");

			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "hr";
			String password = "hr";
			// 2. 데이터베이스 연결 객체(Connection) 생성
			conn = DriverManager.getConnection(url, user, password);

			// 3. SQL 준비/실행 객체(PreparedStatement) 생성
			pst = conn.prepareStatement("insert into web_member values(?, ?, ?, ?)");

			// 4. 바인드 변수 채우기 (바인드변수 순서-1부터 시작, 채워줄 값)
			pst.setString(1, email);
			pst.setString(2, pw);
			pst.setString(3, tel);
			pst.setString(4, address);

			// 5. sql문 실행 후 결과처리
			// cnt : sql 실행시 테이블상에 변화가 일어나는 레코드의 수
			cnt = pst.executeUpdate();

			if (cnt > 0) {
				System.out.println("회원가입성공!");

			}
		} catch (Exception e) {
			System.out.println("회원가입실패");
			e.printStackTrace(); // 예외상황 발생 순서대로 출력
		} finally {
			try {
				pst.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cnt;
	}
}
