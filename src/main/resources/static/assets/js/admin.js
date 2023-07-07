const app = Vue.createApp({
    data() {
        return {
            studentUrl: "/api/students/current",
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
            title: "",
            description: "",
            category: "",
            shift: "",
            name: "",
            idCourse: null
        }
    },
    created() {
        if (isLoggedIn) {
            this.getStudent()
            this.getStudents()
            this.getTeachers()
            this.getCourses()
        }
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
            axios.get("/api/students")
                .then(response => {
                    this.students = response.data.sort(this.order)
                    this.backupStudents = response.data.sort(this.order)
                })
        },
        getTeachers() {
            axios.get("/api/teacher")
                .then(response => {
                    this.teachers = response.data.sort(this.order)
                })
        },
        getCourses() {
            axios.get("/api/courses")
                .then(response => {
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
                    axios.post("/api/logout")
                        .then(() => window.location.href = "/web/index.html")
                }
            })
        },
        courseSelected(id) {
            this.changeCourseSelected = id
        },
        changeTeacher() {
            axios.patch("/api/courses/teacher", "courseID=" + this.changeCourseSelected + "&teacherID=" + this.changeTeacherSelected)
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
            this.removeCourseTeacherID = id
            this.removeCoursesDTOTeacherSelected = this.teachers.find(teacher => teacher.id == id).coursesDTO
        },
        removeCourse() {
            axios.patch("/api/teacher/courses/delete", "teacherID=" + this.removeCourseTeacherID + "&courseID=" + this.removeCourseID)
                .then(response => {
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
            axios.patch('/api/course/disable/' + id)
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
                            this.title = ''
                            this.description = ''
                            this.shift = ''
                            this.category = ''
                            Swal.fire({
                                icon: 'success',
                                title: `${response.data}`,
                            })
                            this.getCourses()
                        }).catch(err => Swal.fire({
                            icon: 'error',
                            title: err.response.data + ''
                        }))
                })
                .catch(err => Swal.fire({
                    icon: 'error',
                    title: err.response.data + ''
                }))
        },

    },
    computed: {
        getStudentsByFilter() {
            if (this.name == "" && this.idCourse != null) {
                axios.get('/api/students/filters', {
                    params: {
                        idCourse: this.idCourse,
                        name: this.name
                    }
                })
                    .then(response => {
                        this.students = response.data;
                    })
                    .catch(err => Swal.fire({
                        icon: 'error',
                        title: err.response.data + ''
                    }));
            } else if (this.idCourse == null && this.name != "") {
                axios.get('/api/students/filters', {
                    params: {
                        idCourse: this.idCourse,
                        name: this.name
                    }
                })
                    .then(response => {
                        this.students = response.data;
                    })
                    .catch(err => Swal.fire({
                        icon: 'error',
                        title: err.response.data + ''
                    }));
            } else if (this.idCourse != null && this.name != "") {
                axios.get('/api/students/filters', {
                    params: {
                        idCourse: this.idCourse,
                        name: this.name
                    }
                })
                    .then(response => {
                        this.students = response.data;
                    })
                    .catch(err => Swal.fire({
                        icon: 'error',
                        title: err.response.data + ''
                    }));
            } else {
                this.students = this.backupStudents
            }
        }
    },
    mounted() {

    }
})
app.mount('#app')