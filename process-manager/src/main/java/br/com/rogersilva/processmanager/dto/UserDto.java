package br.com.rogersilva.processmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import br.com.rogersilva.processmanager.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class UserDto extends Dto {

    private static final long serialVersionUID = -3232703420237052030L;

    @JsonProperty(access = Access.READ_ONLY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Password cannot be blank")
    @JsonProperty(access = Access.READ_WRITE)
    private String password;

    @NotNull(message = "Role cannot be null")
    private Role role;
}