using System.Collections.Generic;
using System.Data.Entity;
using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;

namespace Anytask.MockApi.Models
{
    // You can add profile data for the user by adding more properties to your ApplicationUser class, please visit http://go.microsoft.com/fwlink/?LinkID=317594 to learn more.
    public class ApplicationUser : IdentityUser
    {
        public virtual ICollection<Score> Scores { get; set; } 
        public virtual ICollection<Task> Tasks { get; set; } 
        public virtual ICollection<Course> TeachingCourses { get; set; }
        public virtual ICollection<Course> StudyingCourses { get; set; } 
        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<ApplicationUser> manager, string authenticationType)
        {
            // Note the authenticationType must match the one defined in CookieAuthenticationOptions.AuthenticationType
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);
            // Add custom user claims here
            return userIdentity;
        }
    }

    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext()
            : base("DefaultConnection", throwIfV1Schema: false)
        {
        }
        
        public static ApplicationDbContext Create()
        {
            return new ApplicationDbContext();
        }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<Task>()
                .HasMany(x => x.Students)
                .WithMany(x => x.Tasks)
                .Map(x =>
                {
                    x.ToTable("TaskStudents");
                    x.MapLeftKey("TaskId");
                    x.MapRightKey("StudentId");
                });

            modelBuilder.Entity<Course>()
                .HasMany(x => x.Students)
                .WithMany(x => x.StudyingCourses)
                .Map(x =>
                {
                    x.ToTable("StudentCourses");
                    x.MapLeftKey("StudentId");
                    x.MapRightKey("CourseId");
                });

            modelBuilder.Entity<Course>()
                .HasMany(x => x.Teachers)
                .WithMany(x => x.TeachingCourses)
                .Map(x =>
                {
                    x.ToTable("TeacherCourses");
                    x.MapLeftKey("TeacherId");
                    x.MapRightKey("CourseId");
                });
        }

        public System.Data.Entity.DbSet<Anytask.MockApi.Models.Organization> Organizations { get; set; }

        public System.Data.Entity.DbSet<Anytask.MockApi.Models.Course> Courses { get; set; }

        public System.Data.Entity.DbSet<Anytask.MockApi.Models.Task> Tasks { get; set; }
    }
}