using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using PennyPig_API_Version2.Models;
using PennyPig_API_Version2.Services;

namespace PennyPig_API_Version2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class ExpenseController : ControllerBase
    { 
        private readonly ExpenseService _expenseService;

        public ExpenseController(ExpenseService expenseService)
        {
            _expenseService = expenseService;
        }

        [HttpPost]
        [ActionName("AddExpense")]
        public int AddExpense(string user_id, string category_id, string payment_method, string time, string amount)
        {
            try
            {
                _expenseService.insertExpense(user_id, category_id, payment_method, time, amount);
                return 1;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return 0;
            }
        }

        [HttpPost]
        [ActionName("GetAllExpenses")]
        public ActionResult<List<ExpenseDetails>> GetAllExpenses()
        {
            return _expenseService.Get();
        }

        [HttpPost]
        [ActionName("GetUserExpenses")]
        public ActionResult<List<ExpenseDetails>> GetUserExpense(string user_id)
        {
            return _expenseService.getUserExpenses(user_id);
        }
    }
}
