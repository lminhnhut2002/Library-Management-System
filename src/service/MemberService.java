package service;

import java.util.ArrayList;
import model.Member;
import repository.MemberRepository;
import utils.Validation;

public class MemberService {

    private MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    public ArrayList<Member> getMembers() {
        return repository.getMembers();
    }

    // Tìm thành viên theo ID
    public Member findByID(String memberID) throws Exception {

        if (Validation.isEmpty(memberID)) {
            throw new Exception("Member ID null.");
        }

        if (!Validation.isValidID(memberID)) {
            throw new Exception("Invalid Member ID.");
        }

        return repository.findByID(memberID);
    }

    // Thêm thành viên
    public void addMember(Member member) throws Exception {

        if (member == null) {
            throw new Exception("Member cannot be null.");
        }

        if (findByID(member.getMemberID()) != null) {
            throw new Exception("Member ID already exists.");
        }

        if (Validation.isEmpty(member.getName())
                || Validation.isEmpty(member.getPhone())
                || Validation.isEmpty(member.getEmail())) {
            throw new Exception("Name, Phone and Email cannot be empty.");
        }

        if (!Validation.isValidPhone(member.getPhone())) {
            throw new Exception("Invalid phone.");
        }

        if (!Validation.isValidEmail(member.getEmail())) {
            throw new Exception("Invalid email.");
        }

        member.setName(Validation.toTitleCase(member.getName()));

        repository.add(member);
    }

    // Cập nhật thành viên
    public void updateMember(String memberID, String name,
            String phone, String email) throws Exception {

        Member m = findByID(memberID);

        if (m == null) {
            throw new Exception("Member not found.");
        }

        if (!Validation.isEmpty(name)) {
            m.setName(Validation.toTitleCase(name));
        }

        if (!Validation.isEmpty(phone)) {
            if (!Validation.isValidPhone(phone)) {
                throw new Exception("Phone number invalid.");
            }
            m.setPhone(phone);
        }

        if (!Validation.isEmpty(email)) {
            if (!Validation.isValidEmail(email)) {
                throw new Exception("Email address invalid.");
            }
            m.setEmail(email);
        }
    }

    // Xóa thành viên
    public void removeMember(String memberID) throws Exception {

        Member m = findByID(memberID);

        if (m == null) {
            throw new Exception("Member not found.");
        }

        repository.remove(m);
    }

    // Tìm kiếm thành viên
    public ArrayList<Member> searchMembers(String keyword) {

        if (Validation.isEmpty(keyword)) {
            return new ArrayList<>();
        }

        return repository.search(keyword);
    }
}