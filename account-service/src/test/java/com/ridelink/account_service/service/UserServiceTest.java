package com.ridelink.account_service.service;

import com.ridelink.account_service.repository.UserRepository;
import com.ridelink.account_service.security.JwtService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.mockito.InjectMocks;
import com.ridelink.account_service.model.User;
import com.ridelink.account_service.model.Role;
import com.ridelink.account_service.model.AccountStatus;
import com.ridelink.account_service.dto.UserResponse;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import com.ridelink.account_service.exception.UserNotFoundException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.ridelink.account_service.dto.RegisterRequest;
import static org.mockito.ArgumentMatchers.any;
import com.ridelink.account_service.exception.UserAlreadyExistsException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import com.ridelink.account_service.dto.LoginRequest;
import com.ridelink.account_service.dto.LoginResponse;
import com.ridelink.account_service.exception.InvalidCredentialsException;
import com.ridelink.account_service.exception.AccountNotActiveException;
import com.ridelink.account_service.dto.UpdateUserRequest;
import com.ridelink.account_service.dto.ChangePasswordRequest;

import static org.mockito.Mockito.verify;
import java.util.List;
import com.ridelink.account_service.dto.UpdateStatusRequest;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserService userService;


    @Test
    void getUserById_shouldReturnUser() {





        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@test.com")
                .password("123456")
                .phone("0771234567")
                .role(Role.PASSENGER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponse response = userService.getUserById(1L);

        assertEquals(1L, response.getId());
        assertEquals("Test User", response.getName());
        assertEquals("test@test.com", response.getEmail());
        assertEquals(Role.PASSENGER, response.getRole());
    }
    @Test
    void getUserById_shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());


        assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(99L)
        );
    }
    @Test
    void registerUser_shouldRegisterSuccessfully() {
        RegisterRequest request = new RegisterRequest();

        request.setName("New User");
        request.setEmail("newuser@test.com");
        request.setPassword("123456");
        request.setPhone("0771234567");
        request.setRole(Role.PASSENGER);

        when(userRepository.existsByEmail("newuser@test.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("123456"))
                .thenReturn("hashedPassword");

        User savedUser = User.builder()
                .id(3L)
                .name("New User")
                .email("newuser@test.com")
                .password("hashedPassword")
                .phone("0771234567")
                .role(Role.PASSENGER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.save(any(User.class)))
                .thenReturn(savedUser);

        UserResponse response = userService.registerUser(request);

        assertEquals(3L, response.getId());
        assertEquals("New User", response.getName());
        assertEquals("newuser@test.com", response.getEmail());
    }
    @Test
    void registerUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();

        request.setName("Existing User");
        request.setEmail("existing@test.com");
        request.setPassword("123456");
        request.setPhone("0771234567");
        request.setRole(Role.PASSENGER);
        when(userRepository.existsByEmail("existing@test.com"))
                .thenReturn(true);

        assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.registerUser(request)
        );
    }
    @Test
    void loginUser_shouldLoginSuccessfully() {
        LoginRequest request = new LoginRequest();

        request.setEmail("driver@test.com");
        request.setPassword("123456");

        User user = User.builder()
                .id(2L)
                .name("Test Driver")
                .email("driver@test.com")
                .password("hashedPassword")
                .phone("0712345678")
                .role(Role.DRIVER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findByEmail("driver@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "hashedPassword"))
                .thenReturn(true);

        when(jwtService.generateToken(user))
                .thenReturn("fake-jwt-token");

        LoginResponse response = userService.loginUser(request);

        assertEquals(2L, response.getId());
        assertEquals("driver@test.com", response.getEmail());
        assertEquals("fake-jwt-token", response.getToken());


    }
    @Test
    void loginUser_shouldThrowExceptionWhenPasswordIsWrong() {
        LoginRequest request = new LoginRequest();

        request.setEmail("driver@test.com");
        request.setPassword("wrongpassword");
        User user = User.builder()
                .id(2L)
                .name("Test Driver")
                .email("driver@test.com")
                .password("hashedPassword")
                .phone("0712345678")
                .role(Role.DRIVER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findByEmail("driver@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("wrongpassword", "hashedPassword"))
                .thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> userService.loginUser(request)
        );

    }
    @Test
    void loginUser_shouldThrowExceptionWhenAccountIsNotActive() {
        LoginRequest request = new LoginRequest();

        request.setEmail("driver@test.com");
        request.setPassword("123456");

        User user = User.builder()
                .id(2L)
                .name("Test Driver")
                .email("driver@test.com")
                .password("hashedPassword")
                .phone("0712345678")
                .role(Role.DRIVER)
                .status(AccountStatus.SUSPENDED)
                .build();

        when(userRepository.findByEmail("driver@test.com"))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("123456", "hashedPassword"))
                .thenReturn(true);

        assertThrows(
                AccountNotActiveException.class,
                () -> userService.loginUser(request)
        );
    }
    @Test
    void updateUser_shouldUpdateSuccessfully() {
        UpdateUserRequest request = new UpdateUserRequest();

        request.setName("Updated User");
        request.setPhone("0779999999");

        User user = User.builder()
                .id(1L)
                .name("Old User")
                .email("user@test.com")
                .password("hashedPassword")
                .phone("0771111111")
                .role(Role.PASSENGER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponse response = userService.updateUser(1L, request);

        assertEquals("Updated User", response.getName());
        assertEquals("0779999999", response.getPhone());
    }

    @Test
    void changePassword_shouldChangeSuccessfully() {
        ChangePasswordRequest request = new ChangePasswordRequest();

        request.setCurrentPassword("oldpass123");
        request.setNewPassword("newpass123");

        User user = User.builder()
                .id(1L)
                .name("Test User")
                .email("user@test.com")
                .password("oldHashedPassword")
                .phone("0771234567")
                .role(Role.PASSENGER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches("oldpass123", "oldHashedPassword"))
                .thenReturn(true);

        when(passwordEncoder.encode("newpass123"))
                .thenReturn("newHashedPassword");

        userService.changePassword(1L, request);

        assertEquals("newHashedPassword", user.getPassword());

        verify(userRepository).save(user);
    }
    @Test
    void getAllUsers_shouldReturnAllUsers() {
        User user1 = User.builder()
                .id(1L)
                .name("User One")
                .email("user1@test.com")
                .password("hashedPassword")
                .phone("0771111111")
                .role(Role.PASSENGER)
                .status(AccountStatus.ACTIVE)
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("User Two")
                .email("user2@test.com")
                .password("hashedPassword")
                .phone("0772222222")
                .role(Role.DRIVER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findAll())
                .thenReturn(List.of(user1, user2));

        List<UserResponse> responses = userService.getAllUsers();

        assertEquals(2, responses.size());

        assertEquals("User One", responses.get(0).getName());
        assertEquals("User Two", responses.get(1).getName());
    }

    @Test
    void updateStatus_shouldUpdateSuccessfully() {
        UpdateStatusRequest request = new UpdateStatusRequest();

        request.setStatus(AccountStatus.SUSPENDED);
        User user = User.builder()
                .id(2L)
                .name("Test Driver")
                .email("driver@test.com")
                .password("hashedPassword")
                .phone("0712345678")
                .role(Role.DRIVER)
                .status(AccountStatus.ACTIVE)
                .build();

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponse response = userService.updateStatus(2L, request);
        assertEquals(AccountStatus.SUSPENDED, response.getStatus());
    }



}
