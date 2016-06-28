using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Domain.Anytask
{
    public class CourseTasks
    {
        public ICollection<User> Students;
        public ICollection<Task> Tasks;
        public Dictionary<string, Dictionary<int, Score>> UserTasks;
    }
}
