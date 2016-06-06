using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using Domain.Anytask;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Newtonsoft.Json;
using Task = Domain.Anytask.Task;

namespace Domain.Identity
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit http://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : IdentityUser
    {
        [JsonIgnore]
        public virtual ICollection<Score> Scores { get; set; }
        [JsonIgnore]
        public virtual ICollection<Task> Tasks { get; set; }
        [JsonIgnore]
        public virtual ICollection<Course> TeachingCourses { get; set; }
        [JsonIgnore]
        public virtual ICollection<Course> StudyingCourses { get; set; }
        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<ApplicationUser> manager, string authenticationType)
        {
            // Note the authenticationType must match the one defined in CookieAuthenticationOptions.AuthenticationType
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);
            // Add custom user claims here
            return userIdentity;
        }
    }
}