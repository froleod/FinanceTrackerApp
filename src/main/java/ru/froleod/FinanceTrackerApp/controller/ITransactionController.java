package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.froleod.FinanceTrackerApp.controller.common.ICrudController;
import ru.froleod.FinanceTrackerApp.model.dto.TransactionDTO;

/**
 * REST-интерфейс транзакции
 */
@RestController
@RequestMapping("/transactions")
public interface ITransactionController extends ICrudController<TransactionDTO, String> {

}