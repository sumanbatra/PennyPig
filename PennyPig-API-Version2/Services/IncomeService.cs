using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Driver;
using PennyPig_API_Version2.Models;

namespace PennyPig_API_Version2.Services
{
    public class IncomeService
    {
        private readonly IMongoCollection<IncomeDetails> _income;

        public IncomeService()
        {
            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("penny_pig");

            _income = database.GetCollection<IncomeDetails>("Income_Table");
        }

        public void insertIncome(string user_id, string time, string amount)
        {
            var document = new BsonDocument
            {
                {"user_id", new BsonString(user_id)},
                {"time", new BsonString(time)},
                {"amount", new BsonString(amount)}
            };

            IncomeDetails incomeDetails = new IncomeDetails(user_id, time, amount);

            _income.InsertOne(incomeDetails);
        }

        public List<IncomeDetails> getUserIncomes(string userId)
        {
            try
            {
                List<IncomeDetails> income = new List<IncomeDetails>();
                income = _income.Find(new BsonDocument { { "user_id", userId } }).ToList();
               
                return income;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }

        public List<IncomeDetails> Get() =>
            _income.Find(user => true).ToList();
    }
}
