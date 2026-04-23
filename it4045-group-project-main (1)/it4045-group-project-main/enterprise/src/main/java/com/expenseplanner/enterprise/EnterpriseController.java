package com.expenseplanner.enterprise;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class EnterpriseController {

    /**
     * Handle the root (/) endpoint and return a start page @return (dashboard.html)
     *
     */
    @RequestMapping("/")
    public String index(Model model){
        return ("dashboard");
    }

    /*
     *Handles Page Mapping for Ui
     */
    @GetMapping("/dashboard")
    public String dashboardPage() {
        return "dashboard";
    }
    @GetMapping("/addTransaction")
    public String addTransactionPage() {
        return "addTransaction";
    }

    @GetMapping("/budget")
    public String budgetPage() {
        return "budget";
    }

    @GetMapping("/transactions")
    public String transactionsPage() {
        return "transactions";
    }
    @GetMapping("/account")
    public String accountPage() {
        return "account";
    }

}
