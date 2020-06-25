package in.techsays.hostel.Login_and_Registration;

public class Adminlogin {

    private String Username,Passwored;

    public Adminlogin(String Username, String Passwored) {
        this.Username = Username;
        this.Passwored = Passwored;


    }
    public Adminlogin(){

    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPasswored() {
        return Passwored;
    }

    public void setPasswored(String Passwored) {
        this.Passwored = Passwored;
    }




    @Override
    public String toString() {
        return "adminlogin{" +
                "Username='" + Username + '\'' +
                ", Passwored='" +Passwored + '\'' +
                '}';
    }
}