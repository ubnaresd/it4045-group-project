package com.expenseplanner.enterprise;

import com.expenseplanner.enterprise.dao.InMemoryBudgetRepository;
import com.expenseplanner.enterprise.dao.InMemoryExpenseRepository;
import com.expenseplanner.enterprise.model.Expense;
import com.expenseplanner.enterprise.service.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class EnterpriseApplicationTests {

    private ExpenseServiceImpl expenseService;

    @BeforeEach
    void setUp()
    {
        InMemoryExpenseRepository expenseRepository = new InMemoryExpenseRepository();
        InMemoryBudgetRepository budgetRepository = new InMemoryBudgetRepository();
        expenseService = new ExpenseServiceImpl(expenseRepository, budgetRepository);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void
    givenNoExpenses_whenAddExpense_thenListContainsNewExpenses()
    {
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(50));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");
        expense.setDescription("Dinner");

        Expense saved = expenseService.addExpense(expense);
        List<Expense> allExpenses = expenseService.getAllExpenses();

        assertNotNull(saved.getId());
        assertEquals(1, allExpenses.size());
        assertEquals("Dinner", allExpenses.get(0).getDescription());
    }

    @Test
    void givenExistingExpense_whenUpdateExpense_thenUpdatedFieldsAreReturned()
    {
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(20));
        expense.setDate(LocalDate.now());
        expense.setCategory("Transport");
        expense.setDescription("Bus");

        Expense saved = expenseService.addExpense(expense);

        saved.setDescription("Bus ticket");
        Expense updated = expenseService.updateExpense(saved);

        assertEquals("Bus ticket", updated.getDescription());
        assertEquals(saved.getId(), updated.getId());
    }

    @Test
    void givenExistingExpense_whenDeleteExpense_thenItIsRemovedFromList()
    {
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(10));
        expense.setDate(LocalDate.now());
        expense.setCategory("Snack");
        expense.setDescription("Chips");

        Expense saved = expenseService.addExpense(expense);
        Long id = saved.getId();

        expenseService.deleteExpense(id);
        List<Expense> allExpenses = expenseService.getAllExpenses();

        assertTrue(allExpenses.isEmpty());
    }
}
