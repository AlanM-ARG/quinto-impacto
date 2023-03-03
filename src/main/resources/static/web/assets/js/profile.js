const app = Vue.createApp({
    data() {
        return {
            studentUrl: "/api/students/current",
            teacherUrl: "/api/teacher/current",
            student: "",
            teacher: "",
            newPassword: "",
            oldPassword: ""
        }
    },
    created() {
        this.getStudent()
        this.getTeacher()
    },
    methods: {
        getStudent() {
            axios.get(this.studentUrl)
                .then(response => {
                    this.student = response.data
                })
        },
        getTeacher() {
            axios.get(this.teacherUrl)
                .then(response => {
                    this.teacher = response.data
                })
        },
        getProfileImage() {
            if (this.student.length != 0) {
                return this.student.profileImage
            } else if (this.teacher.length != 0) {
                return this.teacher.profileImage
            }
            return "https://i.ibb.co/yFKrNwB/estudio.png"
        },
        getCoursesTitle() {
            if (this.student != 0) {
                return this.student.coursesStudentsTitle
            } else if (this.teacher != 0) {
                return this.teacher.coursesDTO.map(course => course.title)
            } else {
                return []
            }
        },
        changeProfileImage() {
            let form = document.querySelector('#profileImage');
            let formData = new FormData(form)
            formData.append('upload_preset', 'r16u29xq')
            axios.post('https://api.cloudinary.com/v1_1/dlfic0owc/image/upload', formData)
                .then(response => {
                    if (this.student != 0) {
                        axios.post("/api/students/current/uploadImage", "image=" + response.data.secure_url)
                            .then(() => {
                                this.getStudent()
                                Swal.fire('Imagen Cambiada con exito!', '', 'success')
                            })
                            .catch(err => Swal.fire({
                                icon: 'error',
                                title: err.response.data + ''
                            }))
                    } else if (this.teacher != 0) {
                        axios.post("/api/teacher/current/uploadImage", "image=" + response.data.secure_url)
                            .then(() => {
                                this.getTeacher()
                                Swal.fire('Imagen Cambiada con exito!', '', 'success')
                            })
                            .catch(err => Swal.fire({
                                icon: 'error',
                                title: err.response.data + ''
                            }))
                    }
                })
                .catch(err => Swal.fire({
                    icon: 'error',
                    title: err.response.data + ''
                }))
        },
        changePassword() {
            if (this.student != 0) {
                axios.patch('/api/students/current/changePassword', `password=${this.newPassword}&oldPassword=${this.oldPassword}`)
                    .then(() => {
                        this.newPassword = ''
                        this.oldPassword = ''
                        Swal.fire('Contraseña Cambiada con exito!', '', 'success')
                    })
                    .catch(err => Swal.fire({
                        icon: 'error',
                        title: err.response.data + ''
                    }))
            } else if (this.teacher != 0) {
                axios.patch('/api/teacher/current/changePassword', `password=${this.newPassword}&oldPassword=${this.oldPassword}`)
                    .then(() => {
                        this.newPassword = ''
                        this.oldPassword = ''
                        Swal.fire('Contraseña Cambiada con exito!', '', 'success')
                    })
                    .catch(err => Swal.fire({
                        icon: 'error',
                        title: err.response.data + ''
                    }))
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
                        .then(() => window.location.href = "/web/index.html")
                }
            })
                .catch(err => Swal.fire({
                    icon: 'error',
                    title: err.response.data + ''
                }))
        }
    },
    computed: {

    },
    mounted() {

    }
})
app.mount('#app')