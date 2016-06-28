using System.Collections.Generic;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using Anytask.MockApi.Database;
using Domain.Anytask;
using Task = Domain.Anytask.Task;

namespace Anytask.MockApi.Controllers
{
    [RoutePrefix("api")]
    public class CoursesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Organizations/{id}/Courses
        [Route("Organizations/{id:int}/Courses")]
        [ResponseType(typeof(IQueryable<Course>))]
        public async Task<IHttpActionResult> GetOrganizationCourses(int id)
        {
            var organization = await db.Organizations.FirstOrDefaultAsync(o => o.Id == id);
            if (organization == null)
                return NotFound();
            return Ok(organization.Courses);
        } 

        // GET: api/Users/{id}/StudyingCourses
        [Route("Users/{id}/StudyingCourses")]
        [ResponseType(typeof(IQueryable<Course>))]
        public async Task<IHttpActionResult> GetCoursesStudyingByUser(string id)
        {
            var user = await db.Users
                .Include(u => u.StudyingCourses.Select(c => c.Organization))
                .FirstOrDefaultAsync(u => u.Id == id);
            if (user == null)
                return NotFound();
            return Ok(user.StudyingCourses);
        }

        // GET: api/Users/{id}/TeachingCourses
        [Route("Users/{id}/TeachingCourses")]
        [ResponseType(typeof(IQueryable<Course>))]
        public async Task<IHttpActionResult> GetCoursesTeachedByUser(string id)
        {
            var user = await db.Users
                .Include(u => u.TeachingCourses.Select(c => c.Organization))
                .FirstOrDefaultAsync(u => u.Id == id);
            if (user == null)
                return NotFound();
            return Ok(user.TeachingCourses);
        }

        // GET: api/Courses
        public IQueryable<Course> GetCourses()
        {
            return db.Courses
                .Include(c => c.Organization);
        }

        [Route("Courses/{id}/CourseTasks")]
        public CourseTasks GetCourseTasks(int id)
        {
            var ct = new CourseTasks();
            var course = db.Courses
                .Include(c => c.Organization)
                .FirstOrDefault(c => c.Id == id);
            if (course == null)
                return null;
            var students = course.Students.Select(s => new User { Id = s.Id, Name = s.UserName }).ToList();
            var tasks = course.Tasks;
            ct.Students = students;
            ct.Tasks = tasks;
            var studentTasks = new Dictionary<string, Dictionary<int, Score>>();
            var scores = db.Scores.ToList();
            foreach (var student in students)
            {
                studentTasks[student.Id] = new Dictionary<int, Score>();
                foreach (var task in tasks)
                {
                    var score = scores
                        .FirstOrDefault(s => s.Task.Id == task.Id && s.Student.Id == student.Id);
                    if (score == null)
                    {
                        studentTasks[student.Id][task.Id] = null;
                        continue;
                    }
                    studentTasks[student.Id][task.Id] = new Score
                    {
                        Id = score.Id,
                        Status = score.Status,
                        Comments = score.Comments,
                        Student = null,
                        Task = null,
                        Value = score.Value
                    };
                }
            }
            ct.UserTasks = studentTasks;
            return ct;
        }

        // GET: api/Courses/5
        [ResponseType(typeof(Course))]
        public async Task<IHttpActionResult> GetCourse(int id)
        {
            Course course = await db.Courses
                .Include(c => c.Organization)
                .FirstOrDefaultAsync(c => c.Id == id);
            if (course == null)
            {
                return NotFound();
            }

            return Ok(course);
        }

        // PUT: api/Courses/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutCourse(int id, Course course)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != course.Id)
            {
                return BadRequest();
            }

            db.Entry(course).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!CourseExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Courses
        [ResponseType(typeof(Course))]
        public IHttpActionResult PostCourse(Course course)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Courses.Add(course);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = course.Id }, course);
        }

        // DELETE: api/Courses/5
        [ResponseType(typeof(Course))]
        public IHttpActionResult DeleteCourse(int id)
        {
            Course course = db.Courses.Find(id);
            if (course == null)
            {
                return NotFound();
            }

            db.Courses.Remove(course);
            db.SaveChanges();

            return Ok(course);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool CourseExists(int id)
        {
            return db.Courses.Count(e => e.Id == id) > 0;
        }
    }
}