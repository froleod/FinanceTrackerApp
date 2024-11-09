package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import ru.froleod.FinanceTrackerApp.controller.common.ICrudController;
import ru.froleod.FinanceTrackerApp.model.dto.BankAccountDTO;

/**
 * REST-интерфейс банковского аккаунта
 */
@RequestMapping("/bank-account")
public interface BankAccountController extends ICrudController<BankAccountDTO, String> {

}
