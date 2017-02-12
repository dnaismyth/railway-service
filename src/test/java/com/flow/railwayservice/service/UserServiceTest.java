package com.flow.railwayservice.service;

import com.flow.railwayservice.RailwayserviceApp;
import com.flow.railwayservice.domain.RUser;
import com.flow.railwayservice.dto.Location;
import com.flow.railwayservice.dto.User;
import com.flow.railwayservice.dto.UserRole;
import com.flow.railwayservice.exception.BadRequestException;
import com.flow.railwayservice.exception.ResourceNotFoundException;
import com.flow.railwayservice.repository.UserRepository;
import java.time.ZonedDateTime;
import com.flow.railwayservice.service.util.RandomUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RailwayserviceApp.class)
@Transactional
public class UserServiceTest {

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserService userService;
    
    private String userName = "testuser@flow.com";
    private User user;
    
    @Before
    public void setUp() throws BadRequestException{
    	user = userService.findUserByLogin(userName);
    	if(user == null){
    		user = new User();
    		user.setLogin(userName);
    		user.setPassword("test12");
    		user.setEmail(userName);
    		user.setName("Test user");
    		user.setRole(UserRole.USER);
    		user = userService.createUser(user);
    	}
    }
    
    @After
    public void tearDown(){
    	
    }

    @Test
    public void assertThatOnlyActivatedUserCanRequestPasswordReset() {
        RUser user = userService.createUser("johndoe", "johndoe", "John", "john.doe@localhost");
        Optional<RUser> maybeUser = userService.requestPasswordReset("john.doe@localhost");
        assertThat(maybeUser.isPresent()).isFalse();
        userRepository.delete(user);
    }

    @Test
    public void assertThatResetKeyMustNotBeOlderThan24Hours() {
        RUser user = userService.createUser("johndoe", "johndoe", "John", "john.doe@localhost");

        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(25);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);

        userRepository.save(user);

        Optional<RUser> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());

        assertThat(maybeUser.isPresent()).isFalse();

        userRepository.delete(user);
    }

    @Test
    public void assertThatResetKeyMustBeValid() {
        RUser user = userService.createUser("johndoe", "johndoe", "John", "john.doe@localhost");

        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(25);
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey("1234");
        userRepository.save(user);
        Optional<RUser> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertThat(maybeUser.isPresent()).isFalse();
        userRepository.delete(user);
    }

    @Test
    public void assertThatUserCanResetPassword() {
        RUser user = userService.createUser("johndoe", "johndoe", "John", "john.doe@localhost");
        String oldPassword = user.getPassword();
        ZonedDateTime daysAgo = ZonedDateTime.now().minusHours(2);
        String resetKey = RandomUtil.generateResetKey();
        user.setActivated(true);
        user.setResetDate(daysAgo);
        user.setResetKey(resetKey);
        userRepository.save(user);
        Optional<RUser> maybeUser = userService.completePasswordReset("johndoe2", user.getResetKey());
        assertThat(maybeUser.isPresent()).isTrue();
        assertThat(maybeUser.get().getResetDate()).isNull();
        assertThat(maybeUser.get().getResetKey()).isNull();
        assertThat(maybeUser.get().getPassword()).isNotEqualTo(oldPassword);

        userRepository.delete(user);
    }

    @Test
    public void testUserCanUpdateLocation() throws ResourceNotFoundException{
    	Location loc = new Location();
    	loc.setAddress("Vancouver");
    	loc.setLatitude(49.00);
    	loc.setLongitude(-123.00);
    	User updated = userService.updateUserLocation(user, loc);
    	Assert.assertEquals(loc.getAddress(), updated.getLocation().getAddress());
    	userRepository.delete(user.getId());
    }
}
