using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using PennyPig_API_Version2.Models;
using PennyPig_API_Version2.Services;

namespace PennyPig_API_Version2.Controllers
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class IncomeController
    {
        private readonly IncomeService _incomeService;

        public IncomeController(IncomeService incomeService)
        {
            _incomeService = incomeService;
        }

        [HttpPost]
        [ActionName("AddIncome")]
        public int AddIncome(string user_id, string time, string amount)
        {
            try
            {
                _incomeService.insertIncome(user_id, time, amount);
                return 1;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return 0;
            }
        }

        [HttpPost]
        [ActionName("GetAllIncomes")]
        public ActionResult<List<IncomeDetails>> GetAllIncomes()
        {
            return _incomeService.Get();
        }

        [HttpPost]
        [ActionName("GetUserIncomes")]
        public ActionResult<List<IncomeDetails>> GetUserIncomes(string user_id)
        {
            return _incomeService.getUserIncomes(user_id);
        }
    }
}
