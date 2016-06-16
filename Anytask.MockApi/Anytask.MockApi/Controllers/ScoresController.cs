using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Security.Cryptography.X509Certificates;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.Description;
using Anytask.MockApi.Database;
using Domain.Anytask;

namespace Anytask.MockApi.Controllers
{
    [RoutePrefix("api")]
    public class ScoresController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Tasks/{id}/Scores
        [Route("Tasks/{id}/Scores")]
        [ResponseType(typeof(IQueryable<Score>))]
        public async Task<IHttpActionResult> GetTaskScores(int id)
        {
            var task = await db.Tasks
                .Include(u => u.Scores.Select(s => s.Student))
                .Include(t => t.Course)
                .Include(t => t.Course.Organization)
                .FirstOrDefaultAsync(t => t.Id == id);
            if (task == null)
                return NotFound();
            return Ok(task.Scores
                .Select(GetScoreWithSimplifiedStudentProperty));
        }

        // GET: api/Users/{id}/Scores
        [Route("Users/{id}/Scores")]
        [ResponseType(typeof(IQueryable<Score>))]
        public async Task<IHttpActionResult> GetUserScores(string id)
        {
            var user = await db.Users
                .Include(u => u.Scores.Select(s => s.Task))
                .Include(u => u.Scores.Select(s => s.Task.Course))
                .Include(u => u.Scores.Select(s => s.Task.Course.Organization))
                .FirstOrDefaultAsync(t => t.Id == id);
            if (user == null)
                return NotFound();
            return Ok(user.Scores
                .Select(GetScoreWithSimplifiedStudentProperty));
        }
        
        // GET: api/Scores?taskId={taskId}&userId={userId}
        public async Task<IHttpActionResult> GetSpecificScore(int taskId, string userId)
        {
            var score = await db.Scores
                .Include(s => s.Task)
                .Include(s => s.Task.Course)
                .Include(s => s.Task.Course.Organization)
                .Include(s => s.Student)
                .FirstOrDefaultAsync(s => s.Task.Id == taskId && s.Student.Id == userId);
            if (score == null)
                return NotFound();
            return Ok(GetScoreWithSimplifiedStudentProperty(score));
        }

        private static object GetScoreWithSimplifiedStudentProperty(Score score)
        {
            return new
            {
                score.Id,
                score.Value,
                score.Task,
                Student = new
                {
                    score.Student.Id,
                    score.Student.UserName
                }
            };
        }

        // PUT: api/Scores/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutScore(int id, Score score)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != score.Id)
            {
                return BadRequest();
            }

            db.Entry(score).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ScoreExists(id))
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

        // POST: api/Scores
        [ResponseType(typeof(Score))]
        public IHttpActionResult PostScore(Score score)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Scores.Add(score);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = score.Id }, score);
        }

        // DELETE: api/Scores/5
        [ResponseType(typeof(Score))]
        public IHttpActionResult DeleteScore(int id)
        {
            Score score = db.Scores.Find(id);
            if (score == null)
            {
                return NotFound();
            }

            db.Scores.Remove(score);
            db.SaveChanges();

            return Ok(score);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool ScoreExists(int id)
        {
            return db.Scores.Count(e => e.Id == id) > 0;
        }
    }
}