using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Anytask.MockApi.Models;

namespace Anytask.MockApi.Controllers
{
    [RoutePrefix("api/Users")]
    public class UsersController : ApiController
    {
        private ApplicationDbContext db = ApplicationDbContext.Create();
        
        // GET: /api/Users/Studying?courseId=1
        [Route("Studying")]
        public IQueryable<ApplicationUser> GetUsersStudying(int courseId)
        {
            return db.Users.Where(u => u.StudyingCourses.Any(c => c.Id == courseId));
        }

        // GET: /api/Users/Teaching?courseId=1
        [Route("Teaching")]
        public IQueryable<ApplicationUser> GetUsersTeaching(int courseId)
        {
            return db.Users.Where(u => u.StudyingCourses.Any(c => c.Id == courseId));
        }

        // GET: /api/Users/Performing?taskId=1
        [Route("Performing")]
        public IQueryable<ApplicationUser> GetUsersPerforming(int taskId)
        {
            return db.Users.Where(u => u.Tasks.Any(t => t.Id == taskId));
        }
    }
}
