const app = Vue.createApp({
    data() {
        return {
            studentUrl: "/api/students",
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
        this.alert()
    },
    methods: {
        login() {
            axios.post("/api/login", "email=" + this.loginEmail + "&password=" + this.loginPassword)
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
            axios.post(this.studentUrl, "firstName=" + this.registerFirstName + "&lastName=" + this.registerLastName + "&email=" + this.registerEmail + "&password=" + this.registerPassword)
                .then(() => Swal.fire({
                    icon: 'warning',
                    title: 'Confirme su correo electronico por favor!'
                })
                    .then(() => {
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