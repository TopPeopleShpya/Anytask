using System.Collections.Generic;
using Domain.Identity;
using Newtonsoft.Json;

namespace Domain.Anytask
{
    public class Score
    {
        public int Id { get; set; }
        public ApplicationUser Student { get; set; }
        public Task Task { get; set; }
        public int Value { get; set; }
        public Status Status { get; set; }
        public virtual List<Comment> Comments { get; set; } 
    }
}