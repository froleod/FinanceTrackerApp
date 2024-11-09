package ru.froleod.FinanceTrackerApp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.froleod.FinanceTrackerApp.controller.common.ICrudController;

/**
 * REST-интерфейс транзакции
 */
@RestController
@RequestMapping("/transactions")
public interface TransactionController extends ICrudController<TransactionDTO, String> {

}