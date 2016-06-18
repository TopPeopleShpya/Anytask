namespace Anytask.MockApi.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class Initial : DbMigration
    {
        public override void Up()
        {
            CreateTable(
                "Comments",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Text = c.String(maxLength: 200, storeType: "nvarchar"),
                        Score_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)                
                .ForeignKey("Scores", t => t.Score_Id)
                .Index(t => t.Score_Id);
            
            CreateTable(
                "Courses",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false, maxLength: 200, storeType: "nvarchar"),
                        Organization_Id = c.Int(nullable: false),
                    })
                .PrimaryKey(t => t.Id)                
                .ForeignKey("Organizations", t => t.Organization_Id, cascadeDelete: true)
                .Index(t => t.Organization_Id);
            
            CreateTable(
                "Organizations",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(nullable: false, maxLength: 500, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => t.Id)                ;
            
            CreateTable(
                "AspNetUsers",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                        Email = c.String(maxLength: 256, storeType: "nvarchar"),
                        EmailConfirmed = c.Boolean(nullable: false),
                        PasswordHash = c.String(unicode: false),
                        SecurityStamp = c.String(unicode: false),
                        PhoneNumber = c.String(unicode: false),
                        PhoneNumberConfirmed = c.Boolean(nullable: false),
                        TwoFactorEnabled = c.Boolean(nullable: false),
                        LockoutEndDateUtc = c.DateTime(precision: 0),
                        LockoutEnabled = c.Boolean(nullable: false),
                        AccessFailedCount = c.Int(nullable: false),
                        UserName = c.String(nullable: false, maxLength: 256),
                    })
                .PrimaryKey(t => t.Id)    ;            
//                .Index(t => t.UserName, unique: true, name: "UserNameIndex");
            
            CreateTable(
                "AspNetUserClaims",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        UserId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                        ClaimType = c.String(unicode: false),
                        ClaimValue = c.String(unicode: false),
                    })
                .PrimaryKey(t => t.Id)                
                .ForeignKey("AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "AspNetUserLogins",
                c => new
                    {
                        LoginProvider = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                        ProviderKey = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                        UserId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => new { t.LoginProvider, t.ProviderKey, t.UserId })                
                .ForeignKey("AspNetUsers", t => t.UserId, cascadeDelete: true)
                .Index(t => t.UserId);
            
            CreateTable(
                "AspNetUserRoles",
                c => new
                    {
                        UserId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                        RoleId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => new { t.UserId, t.RoleId })                
                .ForeignKey("AspNetUsers", t => t.UserId, cascadeDelete: true)
                .ForeignKey("AspNetRoles", t => t.RoleId, cascadeDelete: true)
                .Index(t => t.UserId)
                .Index(t => t.RoleId);
            
            CreateTable(
                "Scores",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Value = c.Int(nullable: false),
                        Status = c.Int(nullable: false),
                        Student_Id = c.String(maxLength: 128, storeType: "nvarchar"),
                        Task_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)                
                .ForeignKey("AspNetUsers", t => t.Student_Id)
                .ForeignKey("Tasks", t => t.Task_Id)
                .Index(t => t.Student_Id)
                .Index(t => t.Task_Id);
            
            CreateTable(
                "Tasks",
                c => new
                    {
                        Id = c.Int(nullable: false, identity: true),
                        Name = c.String(maxLength: 200, storeType: "nvarchar"),
                        Description = c.String(maxLength: 200, storeType: "nvarchar"),
                        Deadline = c.DateTime(nullable: false, precision: 0),
                        IsStrict = c.Boolean(nullable: false),
                        MaxScore = c.Int(nullable: false),
                        Course_Id = c.Int(),
                    })
                .PrimaryKey(t => t.Id)                
                .ForeignKey("Courses", t => t.Course_Id)
                .Index(t => t.Course_Id);
            
            CreateTable(
                "AspNetRoles",
                c => new
                    {
                        Id = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                        Name = c.String(nullable: false, maxLength: 256, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => t.Id)                
                .Index(t => t.Name, unique: true, name: "RoleNameIndex");
            
            CreateTable(
                "TaskStudents",
                c => new
                    {
                        TaskId = c.Int(nullable: false),
                        StudentId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => new { t.TaskId, t.StudentId })                
                .ForeignKey("Tasks", t => t.TaskId, cascadeDelete: true)
                .ForeignKey("AspNetUsers", t => t.StudentId, cascadeDelete: true)
                .Index(t => t.TaskId)
                .Index(t => t.StudentId);
            
            CreateTable(
                "StudentCourses",
                c => new
                    {
                        StudentId = c.Int(nullable: false),
                        CourseId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => new { t.StudentId, t.CourseId })                
                .ForeignKey("Courses", t => t.StudentId, cascadeDelete: true)
                .ForeignKey("AspNetUsers", t => t.CourseId, cascadeDelete: true)
                .Index(t => t.StudentId)
                .Index(t => t.CourseId);
            
            CreateTable(
                "TeacherCourses",
                c => new
                    {
                        TeacherId = c.Int(nullable: false),
                        CourseId = c.String(nullable: false, maxLength: 128, storeType: "nvarchar"),
                    })
                .PrimaryKey(t => new { t.TeacherId, t.CourseId })                
                .ForeignKey("Courses", t => t.TeacherId, cascadeDelete: true)
                .ForeignKey("AspNetUsers", t => t.CourseId, cascadeDelete: true)
                .Index(t => t.TeacherId)
                .Index(t => t.CourseId);
            
        }
        
        public override void Down()
        {
            DropForeignKey("AspNetUserRoles", "RoleId", "AspNetRoles");
            DropForeignKey("TeacherCourses", "CourseId", "AspNetUsers");
            DropForeignKey("TeacherCourses", "TeacherId", "Courses");
            DropForeignKey("StudentCourses", "CourseId", "AspNetUsers");
            DropForeignKey("StudentCourses", "StudentId", "Courses");
            DropForeignKey("TaskStudents", "StudentId", "AspNetUsers");
            DropForeignKey("TaskStudents", "TaskId", "Tasks");
            DropForeignKey("Scores", "Task_Id", "Tasks");
            DropForeignKey("Tasks", "Course_Id", "Courses");
            DropForeignKey("Scores", "Student_Id", "AspNetUsers");
            DropForeignKey("Comments", "Score_Id", "Scores");
            DropForeignKey("AspNetUserRoles", "UserId", "AspNetUsers");
            DropForeignKey("AspNetUserLogins", "UserId", "AspNetUsers");
            DropForeignKey("AspNetUserClaims", "UserId", "AspNetUsers");
            DropForeignKey("Courses", "Organization_Id", "Organizations");
            DropIndex("TeacherCourses", new[] { "CourseId" });
            DropIndex("TeacherCourses", new[] { "TeacherId" });
            DropIndex("StudentCourses", new[] { "CourseId" });
            DropIndex("StudentCourses", new[] { "StudentId" });
            DropIndex("TaskStudents", new[] { "StudentId" });
            DropIndex("TaskStudents", new[] { "TaskId" });
            DropIndex("AspNetRoles", "RoleNameIndex");
            DropIndex("Tasks", new[] { "Course_Id" });
            DropIndex("Scores", new[] { "Task_Id" });
            DropIndex("Scores", new[] { "Student_Id" });
            DropIndex("AspNetUserRoles", new[] { "RoleId" });
            DropIndex("AspNetUserRoles", new[] { "UserId" });
            DropIndex("AspNetUserLogins", new[] { "UserId" });
            DropIndex("AspNetUserClaims", new[] { "UserId" });
//            DropIndex("AspNetUsers", "UserNameIndex");
            DropIndex("Courses", new[] { "Organization_Id" });
            DropIndex("Comments", new[] { "Score_Id" });
            DropTable("TeacherCourses");
            DropTable("StudentCourses");
            DropTable("TaskStudents");
            DropTable("AspNetRoles");
            DropTable("Tasks");
            DropTable("Scores");
            DropTable("AspNetUserRoles");
            DropTable("AspNetUserLogins");
            DropTable("AspNetUserClaims");
            DropTable("AspNetUsers");
            DropTable("Organizations");
            DropTable("Courses");
            DropTable("Comments");
        }
    }
}
