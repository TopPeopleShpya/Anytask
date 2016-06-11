using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Anytask
{
    public class User
    {
        public string Id { get; set; }
        public string Name { get; set; }
        public string Email { get; set; }
        public ICollection<Score> Scores { get; set; }
        public ICollection<Task> Tasks { get; set; }
        public ICollection<Course> TeachingCourses { get; set; }
        public ICollection<Course> StudyingCourses { get; set; }
    }
}
