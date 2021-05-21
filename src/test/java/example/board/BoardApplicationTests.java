package example.board;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void testSqlSession() throws Exception{
		//마이바이트 연결 확인
		System.out.println(sqlSessionTemplate.toString());
	}

}
