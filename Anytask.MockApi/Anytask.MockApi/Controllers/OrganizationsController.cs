﻿using System;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Web.Http;
using System.Web.Http.Description;
using Anytask.MockApi.Database;
using Domain.Anytask;

namespace Anytask.MockApi.Controllers
{
    public class OrganizationsController : ApiController
    {
        private ApplicationDbContext db = new ApplicationDbContext();

        // GET: api/Organizations
        [ResponseType(typeof(IQueryable<Organization>))]
        public IHttpActionResult GetOrganizations()
        {
            try
            {
                return Ok(db.Organizations);
            }
            catch (Exception e)
            {
                return InternalServerError(e);
            }
        }

        // GET: api/Organizations/5
        [ResponseType(typeof(Organization))]
        public IHttpActionResult GetOrganization(int id)
        {
            Organization organization = db.Organizations.Find(id);
            if (organization == null)
            {
                return NotFound();
            }

            return Ok(organization);
        }

        // PUT: api/Organizations/5
        [ResponseType(typeof(void))]
        public IHttpActionResult PutOrganization(int id, Organization organization)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != organization.Id)
            {
                return BadRequest();
            }

            db.Entry(organization).State = EntityState.Modified;

            try
            {
                db.SaveChanges();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!OrganizationExists(id))
                {
                    return NotFound();
                }
                throw;
            }

            return StatusCode(HttpStatusCode.NoContent);
        }

        // POST: api/Organizations
        [ResponseType(typeof(Organization))]
        public IHttpActionResult PostOrganization(Organization organization)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Organizations.Add(organization);
            db.SaveChanges();

            return CreatedAtRoute("DefaultApi", new { id = organization.Id }, organization);
        }

        // DELETE: api/Organizations/5
        [ResponseType(typeof(Organization))]
        public IHttpActionResult DeleteOrganization(int id)
        {
            Organization organization = db.Organizations.Find(id);
            if (organization == null)
            {
                return NotFound();
            }

            db.Organizations.Remove(organization);
            db.SaveChanges();

            return Ok(organization);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool OrganizationExists(int id)
        {
            return db.Organizations.Count(e => e.Id == id) > 0;
        }
    }
}