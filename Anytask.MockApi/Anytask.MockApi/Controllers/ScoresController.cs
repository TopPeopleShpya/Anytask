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
    [RoutePrefix("api")]
    public class ScoresController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Tasks/{id}/Scores
        [Route("Tasks/{id}/Scores")]
        [ResponseType(typeof(IQueryable<Score>))]
        public async Task<IHttpActionResult> GetTaskScores(int id)
        {
            var task = await db.Tasks.FirstOrDefaultAsync(t => t.Id == id);
            if (task == null)
                return NotFound();
            return Ok(task.Scores);
        }

        // GET: api/Users/{id}/Scores
        [Route("Users/{id}/Scores")]
        [ResponseType(typeof(IQueryable<Score>))]
        public async Task<IHttpActionResult> GetUserScores(string id)
        {
            var user = await db.Users.FirstOrDefaultAsync(t => t.Id == id);
            if (user == null)
                return NotFound();
            return Ok(user.Scores);
        }

        // GET: api/Scores
        public IQueryable<Score> GetScores()
        {
            return db.Scores;
        }

        // GET: api/Scores/5
        [ResponseType(typeof(Score))]
        public IHttpActionResult GetScore(int id)
        {
            Score score = db.Scores.Find(id);
            if (score == null)
            {
                return NotFound();
            }

            return Ok(score);
        }

        // GET: api/Scores?taskId={taskId}&userId={userId}
        [ResponseType(typeof(Score))]
        public async Task<IHttpActionResult> GetSpecificScore(int taskId, string userId)
        {
            var score = await db.Scores.FirstOrDefaultAsync(s => s.Task.Id == taskId && s.Student.Id == userId);
            if (score == null)
                return NotFound();
            return Ok(score);
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