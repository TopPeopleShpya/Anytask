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
    }
}