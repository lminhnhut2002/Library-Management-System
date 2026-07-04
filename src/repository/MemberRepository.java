package repository;

import java.util.ArrayList;
import model.Member;

public class MemberRepository {

    private ArrayList<Member> members;

    public MemberRepository(ArrayList<Member> members) {
        this.members = members;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }

    // Tìm thành viên theo ID
    public Member findByID(String memberID) {
        for (Member m : members) {
            if (m.getMemberID().equalsIgnoreCase(memberID)) {
                return m;
            }
        }
        return null;
    }

    // Thêm thành viên
    public void add(Member member) {
        members.add(member);
    }

    // Xóa thành viên
    public void remove(Member member) {
        members.remove(member);
    }

    // Tìm kiếm
    public ArrayList<Member> search(String keyword) {
        ArrayList<Member> result = new ArrayList<>();

        keyword = keyword.toLowerCase();

        for (Member m : members) {
            if (m.getMemberID().toLowerCase().contains(keyword)
                    || m.getName().toLowerCase().contains(keyword)
                    || m.getPhone().contains(keyword)
                    || m.getEmail().toLowerCase().contains(keyword)) {

                result.add(m);
            }
        }

        return result;
    }
}