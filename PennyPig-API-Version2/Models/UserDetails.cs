using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace PennyPig_API_Version2.Models
{
    public class UserDetails
    {
        public UserDetails()
        {
        }

        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }

        [BsonElement("name")]
        public string name { get; set; }

        public string email { get; set; }

        public string password { get; set; }
    }
}
