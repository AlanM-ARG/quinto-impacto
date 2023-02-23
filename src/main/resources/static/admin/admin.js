const app = Vue.createApp({
    data() {
        return {
            studentUrl: "http://localhost:8080/api/students/current",
            student: "",
            students: [],
            backupStudents: [],
            teachers: [],
            courses: [],
            changeTeacherSelected: "",
            changeCourseSelected: "",
            removeCourseTeacherID: "",
            removeCoursesDTOTeacherSelected: [],
            removeCourseID: "",
            studentName: "",
            courseFilter: "",
            image: false,
            title:"",
            description:"",
            category:"",
            shift: "",
        }
    },
    created() {
        this.getStudent()
        this.getStudents()
        this.getTeachers()
        this.getCourses()
    },
    methods: {
        order(a, b) {
            return a.id - b.id
        },
        getStudent() {
            axios.get(this.studentUrl)
                .then(response => {
                    this.student = response.data
                })
        },
        getProfileImage() {
            if (this.student.length != 0) {
                return this.student.profileImage
            }
            return "https://cdn-icons-png.flaticon.com/512/7141/7141726.png"
        },
        getStudents() {
            axios.get("http://localhost:8080/api/students")
                .then(response => {
                    this.students = response.data.sort(this.order)
                    this.backupStudents = response.data.sort(this.order)
                })
        },
        getTeachers() {
            axios.get("http://localhost:8080/api/teacher")
                .then(response => {
                    console.log(response);
                    this.teachers = response.data.sort(this.order)
                })
        },
        getCourses() {
            axios.get("http://localhost:8080/api/courses")
                .then(response => {
                    console.log(response);
                    this.courses = response.data.sort(this.order)
                })
        },
        logout() {
            Swal.fire({
                title: '¿Estas seguro de que deseas cerrar sesion?',
                showConfirmButton: true,
                showCancelButton: true,
                confirmButtonColor: '#4356a2',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Si',
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.post("http://localhost:8080/api/logout")
                        .then(() => window.location.href = "http://localhost:8080/web/index.html")
                }
            })
        },
        courseSelected(id) {
            console.log(id);
            this.changeCourseSelected = id
        },
        changeTeacher() {
            axios.patch("http://localhost:8080/api/courses/teacher", "courseID=" + this.changeCourseSelected + "&teacherID=" + this.changeTeacherSelected)
                .then(response => {
                    this.changeTeacherSelected = ''
                    this.changeCourseSelected = ''
                    this.getCourses()
                    this.getTeachers()
                    Swal.fire({
                        icon: 'success',
                        title: `${response.data}`,
                    })
                })
        },
        teacherSelected(id) {
            console.log(id);
            this.removeCourseTeacherID = id
            this.removeCoursesDTOTeacherSelected = this.teachers.find(teacher => teacher.id == id).coursesDTO
        },
        removeCourse() {
            axios.patch("http://localhost:8080/api/teacher/courses/delete", "teacherID=" + this.removeCourseTeacherID + "&courseID=" + this.removeCourseID)
                .then(response => {
                    console.log(response);
                    this.removeCourseTeacherID = ""
                    this.removeCoursesDTOTeacherSelected = []
                    this.removeCourseID = ""
                    this.getCourses()
                    this.getTeachers()
                    Swal.fire({
                        icon: 'success',
                        title: `${response.data}`,
                    })
                })
        },
        disableCourse(id) {
            console.log(id);
            axios.patch('http://localhost:8080/api/course/disable/' + id)
                .then(response => {
                    Swal.fire({
                        icon: 'success',
                        title: `${response.data}`,
                    })
                    this.getCourses()
                })
        },
        createCourse() {
            let form = document.querySelector('#courseImage');
            let formData = new FormData(form)
            formData.append('upload_preset', 'r16u29xq')
            axios.post('https://api.cloudinary.com/v1_1/dlfic0owc/image/upload', formData)
                .then(response => {
                    axios.post("/api/courses", `title=${this.title}&description=${this.description}&coverPage=${response.data.secure_url}&shifts=${this.shift}&category=${this.category}`)
                    .then(response => {
                        this.title=''
                        this.description=''
                        this.shift=''
                        this.category=''
                        Swal.fire({
                            icon: 'success',
                            title: `${response.data}`,
                        })
                        this.getCourses()
                    }).catch(err =>Swal.fire({
                        icon: 'error',
                        title: err.response.data + ''
                    }))
                })
                .catch(err => Swal.fire({
                    icon: 'error',
                    title: err.response.data + ''
                }))
        }
    },
    computed: {
        searchAndFilter() {
            let nameFilter = this.backupStudents.filter(student => student.firstName.toLowerCase().includes(this.studentName.toLowerCase()))
            if (this.courseFilter != "") {
                let studentFilter = []
                nameFilter.forEach(student => {
                    student.coursesStudentsTitle.forEach(course => {
                        if (course == this.courseFilter) {
                            studentFilter.push(student)
                        }
                    });
                })
                this.students = studentFilter
            } else {
                this.students = nameFilter
            }
        }
    },
    mounted() {

    }
})
app.mount('#app')