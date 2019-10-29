using System;
namespace PennyPig_API_Version2.Models
{ 
    public class UserDatabaseSettings : IUserDatabaseSettings
    {
        public string UserDetails { get; set; }
        public string ConnectionString { get; set; }
        public string DatabaseName { get; set; }

        public UserDatabaseSettings()
        {
        }
    }

    public interface IUserDatabaseSettings
    {
        string UserDetails { get; set; }
        string ConnectionString { get; set; }
        string DatabaseName { get; set; }
    }
}
