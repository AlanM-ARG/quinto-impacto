const app = Vue.createApp({
    data() {
        return {
            studentUrl: "http://localhost:8080/api/students/current",
            teacherUrl: "http://localhost:8080/api/teacher/current",
            student: "",
            teacher: "",

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
        getProfileImage(){
            if (this.student.length != 0 ) {
                return this.student.profileImage
            }else if(this.teacher.length != 0){
                return this.teacher.profileImage
            }
            return "https://i.ibb.co/yFKrNwB/estudio.png"
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
        }
    },
    computed: {
        
    },
    mounted() {
        console.log(this.student);
        console.log(this.teacher);
    }
})
app.mount('#app')