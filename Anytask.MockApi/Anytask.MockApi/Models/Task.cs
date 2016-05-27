using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Anytask.MockApi.Models
{
    public class Task
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string Description { get; set; }
        public DateTime Deadline { get; set; }
        public bool IsStrict { get; set; }
        public int MaxScore { get; set; }
        public Course Course { get; set; }
        public virtual ICollection<ApplicationUser> Students { get; set; }
        public virtual ICollection<Score> Scores { get; set; } 
    }
}