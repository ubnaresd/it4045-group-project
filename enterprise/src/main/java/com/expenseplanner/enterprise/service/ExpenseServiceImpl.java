package com.expenseplanner.enterprise.service;

import com.expenseplanner.enterprise.model.Expense;
import com.expenseplanner.enterprise.repository.ExpenseJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of ExpenseService using JPA. Author:ESM
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseJpaRepository repository;
    public ExpenseServiceImpl(ExpenseJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Expense> getAllExpenses() {
        return repository.findAll();
    }

    @Override
    public Expense getExpenseById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Expense addExpense(Expense expense) {
        return repository.save(expense);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        return repository.save(expense);
    }

    @Override
    public void deleteExpense(Long id) {
        repository.deleteById(id);
    }

    
}
