package reagodjj.example.com.getdatafromsystemdemo;

public class Contacts {
    private String name;
    private String phoneNmber;

    public Contacts(String name, String phoneNmber) {
        this.name = name;
        this.phoneNmber = phoneNmber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNmber() {
        return phoneNmber;
    }

    public void setPhoneNmber(String phoneNmber) {
        this.phoneNmber = phoneNmber;
    }
}
