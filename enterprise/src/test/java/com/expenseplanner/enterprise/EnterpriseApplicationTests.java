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

/**
 * Unit tests for ExpenseServiceImpl using Behavior Driven Design (Given/When/Then).
 * Tests are written against the ExpenseService interface using an in-memory repository.
 */
@SpringBootTest
class EnterpriseApplicationTests {

    /** The service under test, initialized with an in-memory repository */
    private ExpenseServiceImpl expenseService;

    /**
     * Sets up a fresh in-memory repository and service before each test.
     */
    @BeforeEach
    void setUp() {
        InMemoryExpenseRepository repository = new InMemoryExpenseRepository();
        expenseService = new ExpenseServiceImpl(repository);
    }

    /** Verifies the Spring application context loads without errors */
    @Test
    void contextLoads() {
    }

    /**
     * Given no expenses exist,
     * When a new expense is added,
     * Then the list contains the new expense with a generated ID.
     */
    @Test
    void givenNoExpenses_whenAddExpense_thenListContainsNewExpense() {
        // Given
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(50));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");
        expense.setDescription("Dinner");

        // When
        Expense saved = expenseService.addExpense(expense);
        List<Expense> allExpenses = expenseService.getAllExpenses();

        // Then
        assertNotNull(saved.getId());
        assertEquals(1, allExpenses.size());
        assertEquals("Dinner", allExpenses.get(0).getDescription());
    }

    /**
     * Given an existing expense,
     * When the expense is updated with new values,
     * Then the updated fields are reflected in the returned expense.
     */
    @Test
    void givenExistingExpense_whenUpdateExpense_thenUpdatedFieldsAreReturned() {
        // Given
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(20));
        expense.setDate(LocalDate.now());
        expense.setCategory("Transport");
        expense.setDescription("Bus");
        Expense saved = expenseService.addExpense(expense);

        // When
        saved.setDescription("Bus ticket");
        Expense updated = expenseService.updateExpense(saved);

        // Then
        assertEquals("Bus ticket", updated.getDescription());
        assertEquals(saved.getId(), updated.getId());
    }

    /**
     * Given an existing expense,
     * When the expense is deleted by ID,
     * Then the expense list is empty.
     */
    @Test
    void givenExistingExpense_whenDeleteExpense_thenItIsRemovedFromList() {
        // Given
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(10));
        expense.setDate(LocalDate.now());
        expense.setCategory("Snack");
        expense.setDescription("Chips");
        Expense saved = expenseService.addExpense(expense);

        // When
        expenseService.deleteExpense(saved.getId());
        List<Expense> allExpenses = expenseService.getAllExpenses();

        // Then
        assertTrue(allExpenses.isEmpty());
    }

    /**
     * Given a negative amount,
     * When addExpense is called,
     * Then an IllegalArgumentException is thrown.
     */
    @Test
    void givenNegativeAmount_whenAddExpense_thenExceptionIsThrown() {
        // Given
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(-5));
        expense.setDate(LocalDate.now());
        expense.setCategory("Food");
        expense.setDescription("Invalid");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> expenseService.addExpense(expense));
    }

    /**
     * Given an expense without an ID,
     * When updateExpense is called,
     * Then an IllegalArgumentException is thrown.
     */
    @Test
    void givenExpenseWithNoId_whenUpdateExpense_thenExceptionIsThrown() {
        // Given
        Expense expense = new Expense();
        expense.setAmount(BigDecimal.valueOf(30));
        expense.setCategory("Bills");
        expense.setDescription("Electric");

        // When / Then
        assertThrows(IllegalArgumentException.class, () -> expenseService.updateExpense(expense));
    }

    /**
     * Given multiple expenses added,
     * When getAllExpenses is called,
     * Then all expenses are returned in the list.
     */
    @Test
    void givenMultipleExpenses_whenGetAll_thenAllAreReturned() {
        // Given
        Expense e1 = new Expense();
        e1.setAmount(BigDecimal.valueOf(10));
        e1.setDate(LocalDate.now());
        e1.setCategory("Food");
        e1.setDescription("Lunch");

        Expense e2 = new Expense();
        e2.setAmount(BigDecimal.valueOf(20));
        e2.setDate(LocalDate.now());
        e2.setCategory("Transport");
        e2.setDescription("Uber");

        // When
        expenseService.addExpense(e1);
        expenseService.addExpense(e2);
        List<Expense> allExpenses = expenseService.getAllExpenses();

        // Then
        assertEquals(2, allExpenses.size());
    }
}

