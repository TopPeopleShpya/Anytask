using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Anytask.MockApi.Models
{
    public class Score
    {
        public int Id { get; set; }
        public ApplicationUser Student { get; set; }
        public Task Task { get; set; }
        public int Value { get; set; }
    }
}