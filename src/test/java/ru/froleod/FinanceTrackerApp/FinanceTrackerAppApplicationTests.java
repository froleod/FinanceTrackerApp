package ru.froleod.FinanceTrackerApp;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import ru.froleod.FinanceTrackerApp.controller.BudgetPageController;
import ru.froleod.FinanceTrackerApp.model.Budget;
import ru.froleod.FinanceTrackerApp.model.FinancialGoal;
import ru.froleod.FinanceTrackerApp.model.User;
import ru.froleod.FinanceTrackerApp.model.enums.GoalStatus;
import ru.froleod.FinanceTrackerApp.repo.FinancialGoalRepository;
import ru.froleod.FinanceTrackerApp.repo.UserRepository;
import ru.froleod.FinanceTrackerApp.service.AppService;
import ru.froleod.FinanceTrackerApp.service.BudgetService;
import ru.froleod.FinanceTrackerApp.service.FinancialGoalService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class FinanceTrackerAppApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test_password_is_encoded_before_saving() {
		UserRepository userRepository = mock(UserRepository.class);
		PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
		AppService appService = new AppService(userRepository, passwordEncoder);

		User user = new User();
		user.setPassword("plainPassword");

		when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

		appService.addUser(user);

		assertEquals("encodedPassword", user.getPassword());
		verify(userRepository).save(user);
	}

	@Test
	public void test_create_budget_with_valid_inputs() {
		BudgetService budgetService = mock(BudgetService.class);
		BudgetPageController controller = new BudgetPageController();
		ReflectionTestUtils.setField(controller, "budgetService", budgetService);

		Budget budget = new Budget();
		Long bankAccountId = 1L;
		String month = "JANUARY";
		String name = "Test Budget";
		BigDecimal amount = new BigDecimal("1000.00");

		String result = controller.createBudget(budget, bankAccountId, month, name, amount);

		verify(budgetService).createBudget(budget, bankAccountId, month, name, amount);
		assertEquals("redirect:/budgets", result);
	}

	@Test
	public void test_create_budget_with_invalid_month() {
		BudgetService budgetService = mock(BudgetService.class);
		BudgetPageController controller = new BudgetPageController();
		ReflectionTestUtils.setField(controller, "budgetService", budgetService);

		Budget budget = new Budget();
		Long bankAccountId = 1L;
		String month = "INVALID_MONTH";
		String name = "Test Budget";
		BigDecimal amount = new BigDecimal("1000.00");

		assertThrows(RuntimeException.class, () -> {
			controller.createBudget(budget, bankAccountId, month, name, amount);
		});
	}

	@Test
	public void test_update_financial_goal_with_valid_inputs() {
		FinancialGoalRepository mockRepository = Mockito.mock(FinancialGoalRepository.class);
		FinancialGoalService service = new FinancialGoalService();
		ReflectionTestUtils.setField(service, "financialGoalRepository", mockRepository);

		Long id = 1L;
		String name = "New Goal Name";
		BigDecimal targetAmount = new BigDecimal("1000.00");
		String startDate = "2023-01-01";
		String endDate = "2023-12-31";
		String status = "ACTIVE";

		FinancialGoal existingGoal = new FinancialGoal();
		existingGoal.setId(id);
		Mockito.when(mockRepository.findById(id)).thenReturn(Optional.of(existingGoal));

		service.updateFinancialGoalForm(id, name, targetAmount, startDate, endDate, status);

		Mockito.verify(mockRepository).save(Mockito.argThat(goal ->
				goal.getName().equals(name) &&
						goal.getTargetAmount().equals(targetAmount) &&
						goal.getStartDate().equals(LocalDate.parse(startDate)) &&
						goal.getEndDate().equals(LocalDate.parse(endDate)) &&
						goal.getStatus() == GoalStatus.ACTIVE
		));
	}

}
