package com.expenseplanner.enterprise;

import com.expenseplanner.enterprise.model.Expense;
import com.expenseplanner.enterprise.service.ExpenseService;
import org.springframework.stereotype.Controller;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Stream;

@Controller
public class EnterpriseController {

    private final ExpenseService expenseService;

    public EnterpriseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @RequestMapping("/")
    public String index(Model model) {
        return dashboardPage(model);
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        model.addAttribute("spentThisMonth", expenseService.getCurrentMonthExpenseTotal());
        model.addAttribute("incomeThisMonth", expenseService.getCurrentMonthIncomeTotal());
        model.addAttribute("categoriesNearLimit", expenseService.getCurrentMonthNearLimitCategoryCount());
        return "dashboard";
    }

    @GetMapping("/addTransaction")
    public String addTransactionPage(Model model) {
        model.addAttribute("budgetStatuses", expenseService.getCurrentMonthBudgets());
        return "addTransaction";
    }

    @PostMapping("/addTransaction")
    public String addTransaction(
            @RequestParam String transactionType,
            @RequestParam BigDecimal amount,
            @RequestParam(required = false) String date,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String description
    ) {
        Expense expense = new Expense();
        BigDecimal normalizedAmount = amount.abs();
        if ("expense".equalsIgnoreCase(transactionType)) {
            normalizedAmount = normalizedAmount.negate();
        }
        expense.setAmount(normalizedAmount);
        expense.setDate((date == null || date.isBlank()) ? LocalDate.now() : LocalDate.parse(date));
        expense.setCategory(category);
        expense.setDescription(description);
        expenseService.addExpense(expense);
        return "redirect:/transactions";
    }

    @GetMapping("/budget")
    public String budgetPage(Model model) {
        model.addAttribute("budgetStatuses", expenseService.getCurrentMonthBudgets());
        model.addAttribute("previousBudgets", expenseService.getPreviousBudgets());
        model.addAttribute("currentMonthValue", YearMonth.now().toString());
        model.addAttribute("allBudgetCount", expenseService.getBudgetCount());
        return "budget";
    }

    @PostMapping("/budget")
    public String addBudget(
            @RequestParam String budgetCategory,
            @RequestParam String budgetMonth,
            @RequestParam BigDecimal budgetAmount
    ) {
        expenseService.addOrUpdateBudget(budgetCategory, budgetMonth, budgetAmount);
        return "redirect:/budget";
    }

    @GetMapping("/transactions")
    public String transactionsPage(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "ANY") String amountRange,
            Model model
    ) {
        Stream<Expense> filtered = expenseService.getAllExpenses().stream();

        if (startDate != null) {
            filtered = filtered.filter(expense -> expense.getDate() != null && !expense.getDate().isBefore(startDate));
        }
        if (endDate != null) {
            filtered = filtered.filter(expense -> expense.getDate() != null && !expense.getDate().isAfter(endDate));
        }
        if (category != null && !category.isBlank() && !"ALL".equalsIgnoreCase(category)) {
            filtered = filtered.filter(expense -> category.equalsIgnoreCase(expense.getCategory()));
        }

        filtered = applyAmountRangeFilter(filtered, amountRange);
        List<Expense> expenses = filtered.toList();

        model.addAttribute("expenses", expenses);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("selectedCategory", category == null || category.isBlank() ? "ALL" : category);
        model.addAttribute("selectedAmountRange", amountRange);
        return "transactions";
    }

    @GetMapping("/account")
    public String accountPage(Model model) {
        model.addAttribute("savedBudgetCount", expenseService.getBudgetCount());
        return "account";
    }

    private Stream<Expense> applyAmountRangeFilter(Stream<Expense> stream, String amountRange) {
        if (amountRange == null || amountRange.isBlank() || "ANY".equalsIgnoreCase(amountRange)) {
            return stream;
        }
        return switch (amountRange) {
            case "RANGE_0_50" -> stream.filter(expense -> isWithin(expense, BigDecimal.ZERO, BigDecimal.valueOf(50)));
            case "RANGE_51_200" -> stream.filter(expense -> isWithin(expense, BigDecimal.valueOf(51), BigDecimal.valueOf(200)));
            case "RANGE_201_500" -> stream.filter(expense -> isWithin(expense, BigDecimal.valueOf(201), BigDecimal.valueOf(500)));
            case "RANGE_500_PLUS" -> stream.filter(expense -> expense.getAmount() != null && expense.getAmount().abs().compareTo(BigDecimal.valueOf(500)) >= 0);
            default -> stream;
        };
    }

    private boolean isWithin(Expense expense, BigDecimal min, BigDecimal max) {
        if (expense.getAmount() == null) {
            return false;
        }
        BigDecimal absolute = expense.getAmount().abs();
        return absolute.compareTo(min) >= 0 && absolute.compareTo(max) <= 0;
    }

}
