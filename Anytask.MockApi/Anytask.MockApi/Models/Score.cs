using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace Anytask.MockApi.Models
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