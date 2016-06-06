using Domain.Identity;
using Newtonsoft.Json;

namespace Domain.Anytask
{
    public class Score
    {
        public int Id { get; set; }
        [JsonIgnore]
        public ApplicationUser Student { get; set; }
        [JsonIgnore]
        public Task Task { get; set; }
        public int Value { get; set; }
    }
}