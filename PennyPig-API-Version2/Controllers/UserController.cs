using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json;
using PennyPig_API_Version2.Models;
using PennyPig_API_Version2.Services;

namespace PennyPig_API_Version2.Controller
{
    [Route("api/[controller]/[action]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly UserService _userService;

        public UserController(UserService userService)
        {
            _userService = userService;
        }

        [HttpGet]
        [ActionName("APITest")]
        public IActionResult APITest()
        {
            Console.WriteLine("API works");
            return null;
        }

        [HttpGet]
        [ActionName("GetAllUsers")]
        public ActionResult<List<UserDetails>> Get() =>
            _userService.Get();

        [HttpPost]
        [ActionName("ValidateUser")]
        public IActionResult ValidateUser(string email, string password)
        {
            string temp = email;
            UserDetails user = _userService.getUser(email, password);
            if(user != null)
            {
                return new ObjectResult(user);
            }
            return new ObjectResult(JsonConvert.SerializeObject("Invalid User"));
        }

        [HttpPost]
        [ActionName("AddUser")]
        public IActionResult AddUser(string email, string password, string name)
        {
            string temp = email;
            UserDetails user = new UserDetails();
            user.email = email;
            user.password = password;
            user.name = name;

            UserDetails addedUser = _userService.Create(user);
            if (addedUser != null)
            {
                return new ObjectResult(addedUser);
            }
            return new ObjectResult(JsonConvert.SerializeObject("Error in adding user"));
        }

        [HttpGet("{id:length(24)}", Name = "GetUser")]
        public ActionResult<UserDetails> Get(string id)
        {
            var user = _userService.Get(id);

            if (user == null)
            {
                return NotFound();
            }

            return user;
        }

        [HttpPost]
        public ActionResult<UserDetails> Create(UserDetails user)
        {
            _userService.Create(user);

            return CreatedAtRoute("GetUser", new { id = user.Id.ToString() }, user);
        }

        [HttpPut("{id:length(24)}")]
        public IActionResult Update(string id, UserDetails userIn)
        {
            var user = _userService.Get(id);

            if (user == null)
            {
                return NotFound();
            }

            _userService.Update(id, userIn);

            return NoContent();
        }

        [HttpDelete("{id:length(24)}")]
        public IActionResult Delete(string id)
        {
            var user = _userService.Get(id);

            if (user == null)
            {
                return NotFound();
            }

            _userService.Remove(user.Id);

            return NoContent();
        }

        [HttpPost]
        [ActionName("ValidateUserEmail")]
        public IActionResult ValidateUserEmail(string email)
        {
            string temp = email;
            UserDetails user = _userService.getUserEmail(email);
            if (user != null)
            {
                return new ObjectResult(user);
            }
            return new ObjectResult(JsonConvert.SerializeObject("Invalid User"));
        }

        [HttpPost]
        [ActionName("InsertUser")]
        public int InsertUser(string name, string password, string email)
        {
            try
            {
                _userService.insertUser(name, password, email);
                return 1;
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                return 0;
            }
        }
    }

}
