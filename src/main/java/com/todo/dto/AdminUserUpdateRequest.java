package com.todo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserUpdateRequest {

    @Pattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "Role must be ROLE_USER or ROLE_ADMIN")
    private String role;

    private Boolean active;
}
