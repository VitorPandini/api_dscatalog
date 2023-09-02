package com.vitorpandini.dscatalog.dto;

import com.vitorpandini.dscatalog.services.validation.UserUpdateValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@UserUpdateValid
public class UserUpdateDTO extends UserDTO{



}
