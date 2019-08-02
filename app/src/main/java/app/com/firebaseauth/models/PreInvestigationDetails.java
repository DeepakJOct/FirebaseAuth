package app.com.firebaseauth.models;

public class PreInvestigationDetails {
    String name, gender, marks, address, caseLodger, contact;
    int age;
    boolean isConfirmed;

    public PreInvestigationDetails() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public PreInvestigationDetails(String name, String gender, String marks, String address, String caseLodger, String contact, int age, boolean isConfirmed) {
        this.name = name;
        this.gender = gender;
        this.marks = marks;
        this.address = address;
        this.caseLodger = caseLodger;
        this.contact = contact;
        this.age = age;
        this.isConfirmed = isConfirmed;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCaseLodger() {
        return caseLodger;
    }

    public void setCaseLodger(String caseLodger) {
        this.caseLodger = caseLodger;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }
}
