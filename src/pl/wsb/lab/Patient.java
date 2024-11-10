package pl.wsb.lab;

public class Patient {
    private String firstName;
    private String lastName;
    private String pesel;



    private String birthDay;
    private int age;
    private String phoneNo;
    private String emailAddress;

    // Konstruktor
    public Patient(String firstName, String lastName, String pesel, String birthDay, int age, String phoneNo, String emailAdress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthDay = birthDay;
        this.age = age;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAdress;
    }

    // Gettery i settery dla każdego pola
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
