package service;

import java.util.ArrayList;
import model.Member;
import utils.Validation;

public class MemberService {

    private ArrayList<Member> members;

    public MemberService(ArrayList<Member> members) {
        this.members = members;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public Member findByID(String memberID) {
        if (Validation.isEmpty(memberID)) {
            return null;
        }
        for (Member m : members) {
            if (m.getMemberID().equalsIgnoreCase(memberID)) {
                return m;
            }
        }
        return null;
    }

    public void addMember(Member member) throws Exception {
        if (member == null) {
            throw new Exception("Member cannot be null.");
        }
        if (!Validation.isValidID(member.getMemberID())) {
            throw new Exception("Invalid Member ID.");
        }
        if (findByID(member.getMemberID()) != null) {
            throw new Exception("Member ID already exists.");
        }
        if (Validation.isEmpty(member.getName())
                || Validation.isEmpty(member.getPhone())
                || Validation.isEmpty(member.getEmail())) {
            throw new Exception("Name, Phone and email cannot be empty.");
        }
        if (!Validation.isValidPhone(member.getPhone())) {
            throw new Exception("Invalid phone.");
        }
        if (!Validation.isValidEmail(member.getEmail())) {
            throw new Exception("Invalid email.");
        }

        member.setName(Validation.toTitleCase(member.getName()));
        members.add(member);
    }

    public void updateMember(String memberID, String name, String phone, String email) throws Exception {

        if (Validation.isEmpty(memberID)) {
            throw new Exception("Member ID null.");
        }
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid Member ID.");
        }
        Member m = findByID(memberID);
        if (m == null) {
            throw new Exception("Member not found.");
        }
       //  Chỉ cập nhật và kiểm tra nếu người dùng có nhập Tên mới
        if (!Validation.isEmpty(name)) {
            m.setName(Validation.toTitleCase(name));
        }

        //  Chỉ kiểm tra định dạng và cập nhật nếu có nhập Số điện thoại mới
        if (!Validation.isEmpty(phone)) {
            if (!Validation.isValidPhone(phone)) {
                throw new Exception("Phone number invalid.");
            }
            m.setPhone(phone);
        }

        // Chỉ kiểm tra định dạng và cập nhật nếu có nhập Email mới
        if (!Validation.isEmpty(email)) {
            if (!Validation.isValidEmail(email)) {
                throw new Exception("Email address invalid.");
            }
            m.setEmail(email);
        }
    }

    //xóa member
    public void removeMember(String memberID) throws Exception {
        if (Validation.isEmpty(memberID)) {
            throw new Exception("Member ID null.");
        }
        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid member ID.");
        }
        Member m = findByID(memberID);
        if (m == null) {
            throw new Exception("Member not found.");
        }

        members.remove(m);
    }

    public ArrayList<Member> searchMembers(String keyword) {
        ArrayList<Member> result = new ArrayList<>();
        if (Validation.isEmpty(keyword)) {
            return result;
        }
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
