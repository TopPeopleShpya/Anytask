using System.Data.Entity;
using Domain.Anytask;
using Domain.Identity;
using Microsoft.AspNet.Identity.EntityFramework;

namespace Anytask.MockApi.Database
{
    public class ApplicationDbContext : IdentityDbContext<ApplicationUser>
    {
        public ApplicationDbContext()
            : base("DefaultConnection", false)
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
    }
}