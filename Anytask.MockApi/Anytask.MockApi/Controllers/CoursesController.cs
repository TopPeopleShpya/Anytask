using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using Anytask.MockApi.Database;
using Domain.Anytask;

namespace Anytask.MockApi.Controllers
{
    public class CoursesController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Organizations/{id}/Courses
        [Route("Organizations/{id}/Courses")]
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
            var user = await db.Users.FirstOrDefaultAsync(u => u.Id == id);
            if (user == null)
                return NotFound();
            return Ok(user.StudyingCourses);
        } 

        // GET: api/Courses
        public IQueryable<Course> GetCourses()
        {
            return db.Courses;
        }

        // GET: api/Courses/5
        [ResponseType(typeof(Course))]
        public IHttpActionResult GetCourse(int id)
        {
            Course course = db.Courses.Find(id);
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