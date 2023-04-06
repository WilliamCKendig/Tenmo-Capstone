package com.techelevator.dao;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JdbcUserDaoTests extends BaseDaoTests{

    private JdbcUserDao sut;

    private static final User USER_1 = new User(1001, "bob", "bob", "USER");
<<<<<<< HEAD
    private static final User USER_2 = new User(1002, "bill", "bill",  "USER");
    private static final User USER_3 = new User(1003, "frank", "frank", "USER");
=======
    private static final User USER_2 = new User(1002, "user", "bill",  "USER");
>>>>>>> 617f62594e8b8becbdd90ba768cb0b43e5aeba3e

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.create("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void findAll_returns_all() {
        List<User> userList = sut.findAll();
        Assert.assertEquals(2, userList.size());
    }

    @Test
    public void findAll_returns_all_users() {
        List<User> userList = sut.findAll();
        assertThat(userList.size()).isEqualTo(2);
//        Assert.assertEquals(3, userList.size());
//        assertUsersMatch(USER_1, userList.get(0));
//        assertUsersMatch(USER_2, userList.get(1));
//        assertUsersMatch(USER_3, userList.get(2));
    }

    private void assertUsersMatch(User expected, User actual) {
        Assert.assertEquals(expected.getUsername(), actual.getUsername());
        Assert.assertEquals(expected.getAuthorities(), actual.getAuthorities());
        Assert.assertEquals(expected.getPassword(), actual.getPassword());
        Assert.assertEquals(expected.getId(), actual.getId());
        Assert.assertEquals(expected.isActivated(), actual.isActivated());
    }
}
