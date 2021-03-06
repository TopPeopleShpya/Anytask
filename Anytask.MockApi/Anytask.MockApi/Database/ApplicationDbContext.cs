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
            System.Data.Entity.Database.SetInitializer(new MySqlInitializer());
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

        public DbSet<Organization> Organizations { get; set; }
        public DbSet<Course> Courses { get; set; }
        public DbSet<Task> Tasks { get; set; }
        public DbSet<Score> Scores { get; set; }
        public DbSet<Comment> Comments { get; set; }
    }
}