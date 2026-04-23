package com.expenseplanner.enterprise.service;

import java.util.List;
import com.expenseplanner.enterprise.model.Expense;

public interface ExpenseService {
    List<Expense> getAllExpenses();
    Expense getExpenseById(Long id);
    Expense addExpense(Expense expense);
    Expense updateExpense(Expense expense);
    void deleteExpense(Long id);
}
