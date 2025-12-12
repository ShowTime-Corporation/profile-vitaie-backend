package showtime_corp.profile_vitaile.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import showtime_corp.profile_vitaile.entity.User;

import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of {@link UserDetails} that adapts
 * the application's {@link User} entity to Spring Security's
 * authentication system.
 *
 * <p>
 * This class provides Spring Security with the required
 * authentication and authorization information derived from
 * your User entity, such as:
 * </p>
 *
 * <ul>
 *     <li>User credentials (email + password)</li>
 *     <li>Account status flags (active, locked, expired)</li>
 *     <li>Assigned roles based on the {@code userSub} enum</li>
 * </ul>
 *
 * <p>
 * The role is formatted using the convention:
 * <b>ROLE_{SUBSCRIPTION_LEVEL}</b>
 * Example: {@code ROLE_FREE}, {@code ROLE_PREMIUM}
 * </p>
 */
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    /**
     * Returns the authorities (roles) granted to the user.
     * <p>
     * The user's subscription type (enum {@code UserSub})
     * is converted into a Spring Security role.
     * </p>
     *
     * @return A collection containing the user's role.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getSub().name()));
    }

    /**
     * Returns the password used to authenticate the user.
     *
     * @return The user's encoded password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the username used to authenticate the user.
     * In this application, the username is the user's email.
     *
     * @return The user's email.
     */
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    /**
     * Indicates whether the user's account has expired.
     * Always returns {@code true} since account expiration
     * logic is not implemented.
     *
     * @return {@code true} (account never expires)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * Always returns {@code true} since account locking
     * is not implemented.
     *
     * @return {@code true} (account is never locked)
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password)
     * have expired.
     * Always returns {@code true} since credential expiration
     * logic is not implemented.
     *
     * @return {@code true} (credentials never expire)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     * Uses the {@code active} field of the User entity.
     *
     * @return {@code true} if the user is active; otherwise false
     */
    @Override
    public boolean isEnabled() {
        return user.getActive();
    }

    /**
     * Exposes the underlying {@link User} entity.
     * Useful when additional user information is needed
     * outside Spring Security.
     *
     * @return The authenticated user entity.
     */
    public User getUser() {
        return user;
    }
}
