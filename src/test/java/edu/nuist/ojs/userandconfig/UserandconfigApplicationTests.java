package edu.nuist.ojs.userandconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.nuist.ojs.baseinfo.entity.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserandconfigApplicationTests {

	@Autowired
	private UserService userService;

	//@Test
	void contextLoads() {
		
	}

	@Test
	void t(){
		userService.findUserBySectionId(8l);
	}

}
