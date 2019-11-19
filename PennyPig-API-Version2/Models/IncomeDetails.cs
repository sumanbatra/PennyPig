using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace PennyPig_API_Version2.Models
{
    public class IncomeDetails
    {
        public IncomeDetails()
        {
        }

        public IncomeDetails(string user_id, string time, string amount)
        {
            this.user_id = user_id;
            this.time = time;
            this.amount = amount;
        }

        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }

        [BsonElement("user_id")]
        public string user_id { get; set; }

        public string time { get; set; }

        public string amount { get; set; }

        public static explicit operator IncomeDetails(BsonDocument v)
        {
            throw new NotImplementedException();
        }
    }
}
