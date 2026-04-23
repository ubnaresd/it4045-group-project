package com.expenseplanner.enterprise.dao;

import com.expenseplanner.enterprise.model.Expense;
import java.util.*;

public class InMemoryExpenseRepository implements ExpenseRepository
{
    private final Map<Long, Expense> expenses = new HashMap<>();
    private long currentId = 0L;

    @Override
    public List<Expense> findAll()
    {
        return new ArrayList<>(expenses.values());
    }

    @Override
    public Optional<Expense> findById(Long id)
    {
        return Optional.ofNullable(expenses.get(id));
    }

    @Override
    public Expense save(Expense expense) {
        if (expense.getId() == null)
        {
            currentId++;
            expense.setId(currentId);
        }
        expenses.put(expense.getId(), expense);
        return expense;
    }

    @Override
    public void deleteById(Long id)
    {
        expenses.remove(id);
    }
}