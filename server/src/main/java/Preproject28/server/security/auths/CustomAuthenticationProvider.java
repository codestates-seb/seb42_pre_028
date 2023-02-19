package Preproject28.server.security.auths;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberDetailsService memberDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = token.getName();
        Optional.ofNullable(username).orElseThrow(()->new UsernameNotFoundException("Invalid User name or User Password"));

        UserDetails userDetails = memberDetailsService.loadUserByUsername(username);
        String password = userDetails.getPassword();
        verifyCredentials(token.getCredentials(),password);

        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

        return UsernamePasswordAuthenticationToken.authenticated(username, password, authorities);
    }

    private void verifyCredentials(Object credentials, String password) {
        if(!passwordEncoder.matches((String)credentials,password)) {
            throw new BadCredentialsException("Invalid User name or User Password");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
