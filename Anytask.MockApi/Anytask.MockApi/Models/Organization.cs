using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Anytask.MockApi.Models
{
    public class Organization
    {
        public int Id { get; set; }
        [StringLength(500)]
        [Required]
        public string Name { get; set; }
        //public ICollection<ApplicationUser> Teachers { get; set; }
        //public ICollection<ApplicationUser> Students { get; set; }
        public virtual ICollection<Course> Courses { get; set; }
    }
}