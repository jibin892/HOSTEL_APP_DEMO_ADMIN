package in.techsays.hostel.Adapter;



public class Payment_Adapter {

    private String Uid,personEmail,Day,Roomnumber,ammount,discription,paymentTime,paymentdate,personName,personPhoto,phone_number,transaction_id,Payment_Method;


    public Payment_Adapter(String Uid, String personEmail,String Day,String Roomnumber,String ammount,String discription,String paymentTime,String paymentdate,
                            String personName,String personPhoto,String phone_number,String transaction_id,String Payment_Method) {
        this.Uid = Uid;
        this.personEmail = personEmail;
        this.Day = Day;
        this.Roomnumber = Roomnumber;
        this.ammount = ammount;
        this.discription = discription;
        this.paymentTime = paymentTime;
        this.paymentdate = paymentdate;

        this.personName = personName;
        this.personPhoto = personPhoto;

        this.phone_number = phone_number;
        this.transaction_id = transaction_id;
        this.Payment_Method = Payment_Method;



    }
    public Payment_Adapter(){

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String Uid) {
        this.Uid = Uid;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String Day) {
        this.Day = Day;
    }

    public String getRoomnumber() {
        return Roomnumber;
    }

    public void setRoomnumber(String Roomnumber) {
        this.Roomnumber = Roomnumber;
    }


    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) {
        this.ammount = ammount;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }



    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(String paymentdate) {
        this.paymentdate = paymentdate;
    }







    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }



    public String getPersonPhoto() {
        return personPhoto;
    }

    public void setPersonPhoto(String personPhoto) {
        this.personPhoto = personPhoto;
    }


    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }



    public String getPayment_Method() {
        return Payment_Method;
    }

    public void setPayment_Method(String Payment_Method) {
        this.Payment_Method = Payment_Method;
    }


}
