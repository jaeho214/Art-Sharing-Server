package kr.ac.skuniv.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//TODO : 대여DB 생각해보자, DB설계를 다시 제대로 해보자

@Entity
public class Rent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentNo;

}
