using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using System.Web.Http.Description;
using Anytask.MockApi.Models;

namespace Anytask.MockApi.Controllers
{
    public class ScoresController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Scores?userId=1&taskId=1
        [ResponseType(typeof(Score))]
        public IHttpActionResult GetScore(string userId, int taskId)
        {
            var score = db.Scores.FirstOrDefault(s => s.Student.Id == userId && s.Task.Id == taskId);
            return score == null ? (IHttpActionResult) NotFound() : Ok(score);
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