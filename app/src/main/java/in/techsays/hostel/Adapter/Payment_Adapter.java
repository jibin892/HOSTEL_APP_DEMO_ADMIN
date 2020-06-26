package in.techsays.hostel.Adapter;



public class Payment_Adapter {

    public String ammount;
  public String  transaction_id;
  public String personPhoto;
   public String personName;
    public String person_id;
    public String paymentTime;
  public String paymentdate;





    public Payment_Adapter(String ammount, String transaction_id, String personPhoto , String personName, String person_id, String paymentTime, String paymentdate ) {
        this.ammount = ammount;
        this.transaction_id = transaction_id;
     this.personPhoto=personPhoto;
      this.personName=personName;
        this.person_id=person_id;
      this.paymentTime=paymentTime;
       this.paymentdate=paymentdate;

        //
    }
    public Payment_Adapter() {
    }

    public String getAmmount() {
        return ammount;
    }

    public void setAmmount(String ammount) { this.ammount = ammount; }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) { this.transaction_id = transaction_id; }


    public String getPersonPhoto() { return personPhoto; }

    public void setPersonPhoto(String personPhoto) { this.personPhoto = personPhoto; }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
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




}