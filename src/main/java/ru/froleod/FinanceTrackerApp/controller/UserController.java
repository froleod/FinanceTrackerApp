package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import ru.froleod.FinanceTrackerApp.controller.common.ICrudController;
import ru.froleod.FinanceTrackerApp.model.dto.UserDTO;

/**
 * REST-интерфейс пользователя
 */
@RequestMapping("/user")
public interface UserController extends ICrudController<UserDTO, String> {

}
