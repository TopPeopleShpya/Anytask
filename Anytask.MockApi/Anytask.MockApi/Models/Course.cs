using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Drawing.Printing;
using System.Linq;
using System.Web;

namespace Anytask.MockApi.Models
{
    public class Course
    {
        public int Id { get; set; }
        [StringLength(200)]
        [Required]
        public string Name { get; set; }
        [Required]
        public Organization Organization { get; set; }
        
        public virtual ICollection<ApplicationUser> Teachers { get; set; }
        public virtual ICollection<ApplicationUser> Students { get; set; }
        public virtual ICollection<Task> Tasks { get; set; }
    }
}