package com.expenseplanner.enterprise;

import com.expenseplanner.enterprise.model.Expense;
import com.expenseplanner.enterprise.service.ExpenseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller that exposes JSON endpoints for expense data.
 * Provides API access to expense operations without a UI layer.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseRestController {

    /** The service layer used to perform expense operations */
    private final ExpenseService expenseService;

    /**
     * Constructor injection of ExpenseService.
     * @param expenseService the service to handle expense business logic
     */
    public ExpenseRestController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Retrieves all expenses as a JSON list.
     * @return list of all Expense objects
     */
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    /**
     * Retrieves a single expense by its ID.
     * @param id the ID of the expense to retrieve
     * @return the matching Expense object, or null if not found
     */
    @GetMapping("/{id}")
    public Expense getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    /**
     * Adds a new expense from a JSON request body.
     * @param expense the Expense object to add
     * @return the saved Expense with its generated ID
     */
    @PostMapping
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    /**
     * Updates an existing expense from a JSON request body.
     * @param expense the Expense object with updated fields
     * @return the updated Expense object
     */
    @PutMapping
    public Expense updateExpense(@RequestBody Expense expense) {
        return expenseService.updateExpense(expense);
    }

    /**
     * Deletes an expense by its ID.
     * @param id the ID of the expense to delete
     */
    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }
}
