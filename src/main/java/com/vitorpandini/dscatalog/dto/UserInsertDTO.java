package com.vitorpandini.dscatalog.dto;

import com.vitorpandini.dscatalog.services.validation.UserInsertValid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@UserInsertValid
public class UserInsertDTO extends UserDTO{

    private String password;


}
