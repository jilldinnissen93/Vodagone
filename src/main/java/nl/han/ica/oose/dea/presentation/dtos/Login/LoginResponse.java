package nl.han.ica.oose.dea.presentation.dtos.Login;

public class LoginResponse {
        private String user;
        private String token;

        public LoginResponse(String user, String token){
            this.user = user;
            this.token = token;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        private void setToken(String token) {
            this.token = token;
        }
}

