using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace PennyPig_API_Version2.Models
{ 
    public class ExpenseDetails
    {
        public ExpenseDetails()
        {
        }

        public ExpenseDetails(string user_id, string category_id, string payment_method, string time, string amount)
        {
            this.user_id = user_id;
            this.category_id = category_id;
            this.payment_method = payment_method;
            this.time = time;
            this.amount = amount;
        }

        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }

        [BsonElement("user_id")]
        public string user_id { get; set; }

        public string category_id { get; set; }

        public string payment_method { get; set; }

        public string time { get; set; }

        public string amount { get; set; }

        public static explicit operator ExpenseDetails(BsonDocument v)
        {
            throw new NotImplementedException();
        }
    }
}
