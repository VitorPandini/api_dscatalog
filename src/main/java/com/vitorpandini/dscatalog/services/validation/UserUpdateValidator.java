package com.vitorpandini.dscatalog.services.validation;

import com.vitorpandini.dscatalog.dto.UserUpdateDTO;
import com.vitorpandini.dscatalog.entities.User;
import com.vitorpandini.dscatalog.repositories.UserRepository;
import com.vitorpandini.dscatalog.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
        var uriVars =(Map<String,String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long userId =Long.parseLong(uriVars.get("id"));
        List<FieldMessage> list = new ArrayList<>();

        User user =repository.findByEmail(dto.getEmail());
        if(user != null && userId != user.getId()){
            list.add(new FieldMessage("Email","Email ja existente"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
        return list.isEmpty();
    }
}