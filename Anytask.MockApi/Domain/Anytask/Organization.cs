using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace Domain.Anytask
{
    public class Organization
    {
        public int Id { get; set; }
        [StringLength(500)]
        [Required]
        public string Name { get; set; }
        [JsonIgnore]
        public virtual ICollection<Course> Courses { get; set; }
    }
}