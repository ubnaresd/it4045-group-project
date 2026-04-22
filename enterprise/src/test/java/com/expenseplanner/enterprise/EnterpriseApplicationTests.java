package com.expenseplanner.enterprise;

import com.expenseplanner.enterprise.model.Expense;
import com.expenseplanner.enterprise.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;


@SpringBootTest
class EnterpriseApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private ExpenseService expenseService;

    @Test
    void testSaveAndRetrieveExpense() {
        // Create test expense
        Expense expense = new Expense();
        expense.setAmount(new BigDecimal("50.00"));
        expense.setCategory("Test");
        expense.setDescription("JUnit Test Expense");
        expense.setDate(LocalDate.now());

        // Save to database
        Expense saved = expenseService.addExpense(expense);

        // Retrieve all
        var allExpenses = expenseService.getAllExpenses();

        // Output for proof
        System.out.println("Saved Expense ID: " + saved.getId());
        System.out.println("All Expenses: " + allExpenses);

        // Basic assertion
        assert saved.getId() != null;
    }
}


