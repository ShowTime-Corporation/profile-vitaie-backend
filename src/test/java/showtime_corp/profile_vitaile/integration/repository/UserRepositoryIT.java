package showtime_corp.profile_vitaile.integration.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import showtime_corp.profile_vitaile.entity.User;
import showtime_corp.profile_vitaile.entity.User.UserSub;
import showtime_corp.profile_vitaile.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryIT {

    @Autowired
    private UserRepository userRepository;

    private User buildUser(String email) {
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail(email);
        user.setPassword("password123");
        user.setSub(UserSub.FREE);
        user.setActive(true);
        return user;
    }

    @Test
    @DisplayName("Should save user successfully")
    void shouldSaveUser() {
        User user = buildUser("test1@mail.com");

        User saved = userRepository.save(user);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getEmail()).isEqualTo("test1@mail.com");
    }

    @Test
    @DisplayName("Should find user by email")
    void shouldFindByEmail() {
        User user = buildUser("find@mail.com");
        userRepository.save(user);

        Optional<User> result =
                userRepository.findByEmail("find@mail.com");

        assertThat(result).isPresent();
        assertThat(result.get().getFirstName()).isEqualTo("Test");
    }

    @Test
    @DisplayName("Should return true when email exists")
    void shouldReturnTrueWhenEmailExists() {
        userRepository.save(buildUser("exists@mail.com"));

        boolean exists =
                userRepository.existsByEmail("exists@mail.com");

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Should return false when email does not exist")
    void shouldReturnFalseWhenEmailDoesNotExist() {
        boolean exists =
                userRepository.existsByEmail("no@mail.com");

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Should fail when saving duplicated email")
    void shouldFailWhenDuplicatedEmail() {
        userRepository.save(buildUser("dup@mail.com"));

        User duplicated = buildUser("dup@mail.com");

        assertThatThrownBy(() -> userRepository.saveAndFlush(duplicated))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
