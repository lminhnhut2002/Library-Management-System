package model;

public class RegularMember extends Member {

    public RegularMember(String memberID, String name, String phone, String email) {
        super(memberID, name, phone, email);
    }

    @Override
    public int getBorrowLimit() {
        return 3;
    }

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 5000;
    }

    @Override
    public int getNumberDaysBorrow() {
        return 7;
    }

    @Override
    public String getType() {
        return "Regular";
    }
}
