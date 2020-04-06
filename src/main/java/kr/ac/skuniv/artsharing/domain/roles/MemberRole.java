package kr.ac.skuniv.artsharing.domain.roles;

import lombok.Getter;

@Getter
public enum MemberRole {
    CLIENT("일반회원"), ARTIST("예술가");

    private String role;
    MemberRole(String role){
        this.role = role;
    }

    public static MemberRole fromString(String requestString){
        for(MemberRole role : MemberRole.values()){
            if(role.getRole().equals(requestString))
                return role;
        }
        return null;
    }
}
