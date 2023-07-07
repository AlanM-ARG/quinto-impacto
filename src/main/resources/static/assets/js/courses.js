const app = Vue.createApp({
    data() {
        return {
            studentUrl: "/api/students/current",
            teacherUrl: "/api/teacher/current",
            student: "",
            teacher: "",
            courses: "",

        }
    },
    created() {
        if (isLoggedIn) {
            this.getStudent()
            this.getTeacher()
        }
        this.getCourses()
    },
    methods: {
        getStudent() {
            axios.get(this.studentUrl)
                .then(response => {
                    this.student = response.data
                })
                .catch(() => '')
        },
        getTeacher() {
            axios.get(this.teacherUrl)
                .then(response => {
                    this.teacher = response.data
                })
                .catch(() => '')
        },
        getProfileImage() {
            if (this.student.length != 0) {
                return this.student.profileImage
            } else if (this.teacher.length != 0) {
                return this.teacher.profileImage
            }
            return "https://i.ibb.co/yFKrNwB/estudio.png"
        },
        getCourses() {
            axios.get("/api/courses/active")
                .then(response => {
                    this.courses = response.data.sort((a, b) => a.id - b.id)
                })
        },
        registerCourse(courseID) {
            if (this.student.length == 0 && this.teacher.length == 0) {
                Swal.fire({
                    title: 'Debes iniciar sesion para inscribirte a un curso',
                    icon: "warning",
                    showConfirmButton: true,
                    showCancelButton: true,
                    confirmButtonColor: '#4356a2',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Iniciar Sesion',
                }).then((result) => {
                    if (result.isConfirmed) {
                        window.location.href = '/login'
                    }
                })
            } else {
                Swal.fire({
                    title: '¿Estas seguro de que deseas inscribirte a este curso?',
                    icon: "warning",
                    showConfirmButton: true,
                    showCancelButton: true,
                    confirmButtonColor: '#4356a2',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Si, inscribirme.'
                }).then((result) => {
                    if (result.isConfirmed) {
                        axios.post("/api/courseStudent", "courseID=" + courseID)
                            .then(response =>
                                Swal.fire({
                                    icon: 'success',
                                    title: response.data,
                                })
                            )
                            .catch(error => Swal.fire({
                                icon: 'error',
                                title: error.response.data,
                            }))
                    }
                })
            }

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
                        .then(() => window.location.href = "/")
                }
            })
        }
    },
    computed: {

    },
    mounted() {

    }
})
app.mount('#app')