using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Driver;
using PennyPig_API_Version2.Models;

namespace PennyPig_API_Version2.Services
{
    public class ExpenseService
    {
        private readonly IMongoCollection<ExpenseDetails> _expense;

        public ExpenseService()
        { 
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("penny_pig");

            _expense = database.GetCollection<ExpenseDetails>("Expense_Table");
        }

        public ExpenseDetails getUser(string user_id)
        {
            try
            {
                ExpenseDetails expense = _expense.Find<ExpenseDetails>(new BsonDocument { { "user_id", user_id } }).FirstAsync().Result;
                return expense;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }

        public void insertExpense(string user_id, string category_id, string payment_method, string time, string amount)
        {
            var document = new BsonDocument
            {
                {"user_id", new BsonString(user_id)},
                {"category_id", new BsonString(category_id)},
                {"payment_method", new BsonString(payment_method)},
                {"time", new BsonString(time)},
                {"amount", new BsonString(amount)}
            };

            ExpenseDetails expenseDetails = new ExpenseDetails(user_id, category_id, payment_method, time, amount);

            _expense.InsertOne(expenseDetails);
        }

        public List<ExpenseDetails> Get() =>
            _expense.Find(user => true).ToList();
    }
}
