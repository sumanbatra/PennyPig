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

        public UserDetails(string name, string password, string email)
        {
            this.name = name;
            this.email = email;
            this.password = password;
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
