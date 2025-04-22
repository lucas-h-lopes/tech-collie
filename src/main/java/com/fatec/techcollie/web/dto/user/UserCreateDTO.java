package com.fatec.techcollie.web.dto.user;

import com.fatec.techcollie.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public record UserCreateDto(    @NotBlank(message = "Username can't be null, empty or contain only white spaces")
                                @Size(min = 3, max = 15, message = "Username must contain 3-15 characters")
                                String username,
                                @NotBlank(message = "Name can't be null, empty or contain only white spaces")
                                @Size(min = 3, max = 50, message = "Name must contain 3-50 characters")
                                String name,
                                @NotBlank(message = "Surname can't be null, empty or contain only white spaces")
                                @Size(min = 3, max = 50, message = "Surname must contain 3-50 characters")
                                String surname,
                                @NotBlank(message = "Email can't be null, empty or contain only white spaces")
                                @Pattern(regexp = "^[A-Za-z0-9._]{3,}@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email must follow the pattern 'lucas.hen@email.com'")
                                String email,
                                @NotBlank(message = "Password can't be null, empty or contain only white spaces")
                                @Size(min = 8, message = "Password must contain at least 8 characters")
                                @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                                        message = "Password must be at least 8 characters long, " +
                                                "including one uppercase letter, one number, and one special character")
                                String password) {
}
