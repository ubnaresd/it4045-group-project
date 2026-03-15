# Expense Planner - Design Document

## Introduction
Our application is a web-based Expenses Planner that allows individuals to track and plan their spending in one place. The target users are those who struggle to keep track of all the bills they have to pay and save money more consistently. The system will allow the users to record their income and expenses, group transactions into different categories (such as rent, food, utilities, and subscriptions), and use a budgeting feature to see how much money they have left over to spend in each category for the month.

## Storyboard
![Image](https://github.com/user-attachments/assets/a41804a7-e288-42cf-b6c2-2c48a8577494)

## User Stories 
- As a user, I want to be able to create an account and log in so that I can securely access my expense data.<br>
- As a user, I want to be able to add new transactions (income and expenses) with details such as amount, date, category, and description so that I can keep track of my spending.<br>
- As a user, I want to be able to view my transactions in a list format and filter them by date, category, or amount so that I can easily analyze my spending habits.<br>
- As a user, I want to be able to set a monthly budget for each category so that I can manage my finances better and avoid overspending.<br>
- As a user, I want to easily see when I am close to reaching my budget limit for a category so that I can adjust my spending accordingly.

## Class Diagram 
<img width="740" height="807" alt="Image" src="https://github.com/user-attachments/assets/80edddaa-2222-4b6c-b0ed-2595e6f75fce" />

## JSON Schema 
{
"title": "Expense Planner",
  "type": "object",

  "properties": {
    "user": { "$ref": "#/$defs/User" },
    "category": {"$ref": "#/$defs/Category"},
    "transaction": {"$ref": "#/$defs/Transaction"}
    
  },

  "$defs": {
    "User": {
      "title": "User",
      "type": "object",
      "required": ["userId", "userName", "email", "passwordHash"],
      "properties": {
        "userId": { "type": "integer" },
        "userName": { "type": "string", "minLength": 1 },
        "email": { "type": "string", "format": "email" },
        "passwordHash": { "type": "string", "minLength": 1 }
      }
    },
    "Category": {
        "title": "Category",
        "type": "object",
        "required": ["categoryId", "name", "userId"],
        "properties": {
            "categoryId": {
                "type": "integer"
            },
            "name": {
                "type": "string",
                "minLength": 1
            },
            "userId": {
                "type": "integer"
            }
           }
    },
    "Transaction": {
      "title": "Transaction",
      "type": "object",
      "required": ["expenseId", "amount", "email", "description", "category", "date", "user"],
      "properties": {
                "expenseId": {
                    "type": "integer"
                },
                "amount": {
                    "type": "number"
                },
                "description": {
                    "type": "string"
                },
                "category": {
                    "type": "string"
                },
                "date": {
                    "type": "string",
                    "format": "date"
                },
                "user": {
                    "type": "string"
                }
        }
    }
    
  }
}
## Scrum Roles
Product Owner: Jonas<br>
Scrum Master: Pranish<br>
Developers: Ethan<br>
DevOps: Shrutika

## Github Link
https://github.com/ubnaresd/it4045-group-project.git

## Scrum Board
https://github.com/users/ubnaresd/projects/1

## Teams Link
Weekly Stand-up Meeting
Platform: Microsoft Teams
Time: Sunday 8:00 PM
Link:https://teams.microsoft.com/l/chat/19:5ffa9121024848d3a9baec53f26a2a4f@thread.v2/conversations?context=%7B%22contextType%22%3A%22chat%22%7D
