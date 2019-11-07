using System;
using System.Collections.Generic;
using MongoDB.Bson;
using MongoDB.Driver;
using PennyPig_API_Version2.Models;

namespace PennyPig_API_Version2.Services
{
    public class UserService
    {
        private readonly IMongoCollection<UserDetails> _users;

        public UserService(IUserDatabaseSettings settings)
        {
            // var client = new MongoClient(settings.ConnectionString);
            // var database = client.GetDatabase(settings.DatabaseName);

            var client = new MongoClient("mongodb://localhost:27017");
            var database = client.GetDatabase("penny_pig");

            // _users = database.GetCollection<UserDetails>(settings.UserDetails);
            _users = database.GetCollection<UserDetails>("users");
        }

        public List<UserDetails> Get() =>
            _users.Find(user => true).ToList();

        public UserDetails Get(string id) =>
            _users.Find<UserDetails>(user => user.Id == id).FirstOrDefault();

        public UserDetails Create(UserDetails user)
        {
            try
            {
                _users.InsertOne(user);
                return user;
            }
            catch(Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }

        public void Update(string id, UserDetails userIn) =>
            _users.ReplaceOne(user => user.Id == id, userIn);

        public void Remove(UserDetails userIn) =>
            _users.DeleteOne(user => user.Id == userIn.Id);

        public void Remove(string id) =>
            _users.DeleteOne(user => user.Id == id);

        public UserDetails getUser(string email, string password)
        {
            try
            {
                UserDetails user = _users.Find<UserDetails>(new BsonDocument { { "email", email }, { "password", password } }).FirstAsync().Result;
                return user;
            }
            catch(Exception e)
            {
                Console.WriteLine(e);
                return null;
            }
        }
    }
}
