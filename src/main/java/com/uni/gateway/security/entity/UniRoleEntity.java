package com.uni.gateway.security.entity;

import lombok.Data;


/**
 * 角色表
 *
 * @author gehoubao
 * @create 2020-03-31 11:25
 **/
@Data
@Entity
@Table(name = "uni_role")
public class UniRoleEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name_abbr", columnDefinition = "VARCHAR(255) COMMENT '角色名称简写'")
    private String nameAbbr;

    @Column(name = "name", columnDefinition = "VARCHAR(255) COMMENT '角色名称'")
    private String name;

    @Column(name = "status", columnDefinition = "TINYINT COMMENT '用户状态，0正常，1注销'")
    private int status;

    public UniRoleEntity() {
    }

    public UniRoleEntity(SecurityRoleVO vo) {
        this.name = vo.getName();
        this.nameAbbr = vo.getNameAbbr();
    }
}