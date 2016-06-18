using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using Domain.Identity;
using Newtonsoft.Json;

namespace Domain.Anytask
{
    public class Course
    {
        public int Id { get; set; }

        [StringLength(200)]
        [Required]
        public string Name { get; set; }

        [Required]
        public Organization Organization { get; set; }

        [JsonIgnore]
        public virtual ICollection<ApplicationUser> Teachers { get; set; }

        [JsonIgnore]
        public virtual ICollection<ApplicationUser> Students { get; set; }

        [JsonIgnore]
        public virtual ICollection<Task> Tasks { get; set; }
    }
}
