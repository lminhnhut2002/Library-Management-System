package model;

public class PremiumMember extends Member {

    public PremiumMember(String memberID, String name, String phone, String email) {
        super(memberID, name, phone, email);
    }

    @Override
    public int getBorrowLimit() {
        return 5;
    }

    @Override
    public double calculateFine(int overdueDays) {
        return overdueDays * 3000;
    }

    @Override
    public String getType() {
        return "Premium";
    }

    @Override
    public int getNumberDaysBorrow() {
        return 14;
    }
}
