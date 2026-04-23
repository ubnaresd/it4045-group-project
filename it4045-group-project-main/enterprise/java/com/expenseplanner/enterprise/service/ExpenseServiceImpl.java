package com.expenseplanner.enterprise.service;

import com.expenseplanner.enterprise.dao.ExpenseRepository;
import com.expenseplanner.enterprise.model.Expense;
import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository)
    {
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<Expense> getAllExpenses()
    {
        return expenseRepository.findAll();
    }

    @Override
    public Expense getExpenseById(Long id)
    {
        return expenseRepository.findById(id).orElse(null);
    }

    @Override
    public Expense addExpense(Expense expense)
    {
        if (expense.getAmount() == null || expense.getAmount().signum() < 0)
        {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        expense.setId(null);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense updateExpense(Expense expense)
    {
        if (expense.getId() == null)
        {
            throw new IllegalArgumentException("Cannot update expense without id");
        }
        return expenseRepository.save(expense);
    }

    @Override
    public void deleteExpense(Long id)
    {
        expenseRepository.deleteById(id);
    }
}