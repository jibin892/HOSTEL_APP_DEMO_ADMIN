package in.techsays.hostel.Login_and_Registration;

public class User {

    private String Uid,Email,Profile_image,Name,Date,Room_Number,Phone,Home_phome,Address;

    public User(String Uid, String Email,String Profile_image,String Name,String Date,String Room_Number,String Phone,String Home_phome,String Address) {
        this.Uid = Uid;
        this.Email = Email;
        this.Profile_image = Profile_image;
        this.Name = Name;
        this.Date = Date;
        this.Room_Number = Room_Number;
        this.Phone = Phone;
        this.Home_phome = Home_phome;
        this.Address = Address;



    }
    public User(){

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getProfile_image() {
        return Profile_image;
    }

    public void setProfile_image(String Profile_image) {
        this.Profile_image = Profile_image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getRoom_Number() {
        return Room_Number;
    }

    public void setRoom_Number(String Room_Number) {
        this.Room_Number = Room_Number;
    }



    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getHome_phome() {
        return Home_phome;
    }

    public void setHome_phome(String Home_phome) {
        this.Home_phome = Home_phome;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }
    @Override
    public String toString() {
        return "registration{" +
                "Uid='" + Uid + '\'' +
                ", Email='" +Email + '\'' +
                ", Profile_image='" +Profile_image + '\'' +
                ", Name='" +Name + '\'' +
                ", Date='" +Date + '\'' +
                ", Room_Number='" +Room_Number + '\'' +
                ", Phone='" +Phone + '\'' +
                ", Home_phome='" +Home_phome + '\'' +
                ", Address='" +Address + '\'' +
                '}';
    }
}