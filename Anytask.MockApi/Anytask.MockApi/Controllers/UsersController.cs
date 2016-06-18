using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Anytask.MockApi.Database;
using Domain.Anytask;

namespace Anytask.MockApi.Controllers
{
    [RoutePrefix("api")]
    public class UsersController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        [Route("Course/{id:int}/Students")]
        public IHttpActionResult GetCourseStudents(int courseId)
        {
            var course = db.Courses.Find(courseId);
            if (course == null)
                return NotFound();
            return Ok(course.Students.Select(s => new User {Id = s.Id, Name = s.UserName}));
        }
        [Route("Course/{id:int}/Teachers")]
        public IHttpActionResult GetCourseTeachers(int courseId)
        {
            var course = db.Courses.Find(courseId);
            if (course == null)
                return NotFound();
            return Ok(course.Teachers.Select(s => new User { Id = s.Id, Name = s.UserName }));
        }
    }
}
