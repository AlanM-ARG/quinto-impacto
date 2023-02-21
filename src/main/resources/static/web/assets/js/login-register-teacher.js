const app = Vue.createApp({
    data() {
        return {
            teacherUrl: "http://localhost:8080/api/teacher",
            loginEmail: "",
            loginPassword: "",
            registerFirstName: "",
            registerLastName: "",
            registerEmail: "",
            registerPassword: "",
            error: ""
        }
    },
    created() {

    },
    methods: {
        login() {
            axios.post("http://localhost:8080/api/login", "email=" + this.loginEmail + "&password=" + this.loginPassword)
                .then(response => {
                    window.location.href = "/"
                    console.log(response);
                })
                .catch(error => {
                    this.error = error.response.data
                    console.log(error);
                    if (this.error.status == 401) {
                        if (this.loginEmail == "") {
                            Swal.fire({
                                icon: 'error',
                                title: "Ingrese un correo electronico",
                            })
                        } else if (this.loginPassword == "") {
                            Swal.fire({
                                icon: 'error',
                                title: "Ingrese una contraseña",
                            })
                        } else {
                            Swal.fire({
                                icon: 'error',
                                title: 'Correo electronico o contraseña incorrecta',
                            })
                        }
                    }
                })
        },
        register() {
            axios.post(this.teacherUrl, "firstName=" + this.registerFirstName + "&lastName=" + this.registerLastName + "&email=" + this.registerEmail + "&password=" + this.registerPassword)
                .then(() => Swal.fire('Confirme su correo electronico por favor!', 'warning')
                .then(()=>{
                    this.registerFirstName = ""
                    this.registerLastName = ""
                    this.registerEmail = ""
                    this.registerPassword = ""
                }))
                .catch(error => {
                    console.log(error);
                    this.error = error.response.data
                        Swal.fire({
                            icon: 'error',
                            title: `${this.error}`,
                        })
                })
        },
        alert() {
            let urlParams = new URLSearchParams(window.location.search)
            let value = urlParams.get('confirmed')
            if (value == 'true') {
                Swal.fire({
                    icon: 'success',
                    title: 'Correo Electronico Confirmado!'
                })
            }
        }
    },
    computed: {
        
    },
    mounted() {

    }
})
app.mount('#app')