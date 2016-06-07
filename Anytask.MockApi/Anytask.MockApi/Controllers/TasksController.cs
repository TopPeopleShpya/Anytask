using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using Anytask.MockApi.Database;
using Task = Domain.Anytask.Task;

namespace Anytask.MockApi.Controllers
{
    public class TasksController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Courses/{id}/Tasks
        [Route("Courses/{id}/Tasks")]
        [ResponseType(typeof(IQueryable<Task>))]
        public async Task<IHttpActionResult> GetCourseTasks(int id)
        {
            var course = await db.Courses.FirstOrDefaultAsync(c => c.Id == id);
            if (course == null)
                return NotFound();
            return Ok(course.Tasks);
        }

        // GET: api/Users/{id}/Tasks
        [Route("Users/{id}/Tasks")]
        [ResponseType(typeof(IQueryable<Task>))]
        public async Task<IHttpActionResult> GetUserTasks(string id)
        {
            var user = await db.Users.FirstOrDefaultAsync(c => c.Id == id);
            if (user == null)
                return NotFound();
            return Ok(user.Tasks);
        }

        // GET: api/Tasks
        public IQueryable<Task> GetTasks()
        {
            return db.Tasks;
        }

        // GET: api/Tasks/5
        [ResponseType(typeof(Task))]
        public IHttpActionResult GetTask(int id)
        {
            Task task = db.Tasks.Find(id);
            if (task == null)
            {
                return NotFound();
            }

            return Ok(task);
        }

        // PUT: api/Tasks/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutTask(int id, Task task)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != task.Id)
            {
                return BadRequest();
            }

            db.Entry(task).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TaskExists(id))
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

        // POST: api/Tasks
        [ResponseType(typeof(Task))]
        public IHttpActionResult PostTask(Task task)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Tasks.Add(task);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = task.Id }, task);
        }

        // DELETE: api/Tasks/5
        [ResponseType(typeof(Task))]
        public IHttpActionResult DeleteTask(int id)
        {
            Task task = db.Tasks.Find(id);
            if (task == null)
            {
                return NotFound();
            }

            db.Tasks.Remove(task);
            db.SaveChanges();

            return Ok(task);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool TaskExists(int id)
        {
            return db.Tasks.Count(e => e.Id == id) > 0;
        }
    }
}