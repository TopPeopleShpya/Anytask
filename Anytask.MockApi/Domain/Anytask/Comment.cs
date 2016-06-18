using System.ComponentModel.DataAnnotations;

namespace Domain.Anytask
{
    public class Comment
    {
        public int Id { get; set; }

        [StringLength(200)]
        public string Text { get; set; }
    }
}
