package com.expenseplanner.enterprise.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.expenseplanner.enterprise.model.Expense;

/**
 * Repository interface for Expense.
 * Automatically provides CRUD operations via Spring Data JPA.
 */
public interface ExpenseJpaRepository extends JpaRepository<Expense, Long> {
}