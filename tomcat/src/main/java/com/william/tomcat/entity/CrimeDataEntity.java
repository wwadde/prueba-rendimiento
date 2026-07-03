package com.william.tomcat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "crime_data_from_2020_to_2024")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrimeDataEntity {

    @Id
    @Column(name = "dr_no")
    private Long drNo;

    @Column(name = "date_rptd")
    private String dateRptd;

    @Column(name = "date_occ")
    private String dateOcc;

    @Column(name = "time_occ")
    private Long timeOcc;

    @Column(name = "area")
    private Long area;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "rpt_dist_no")
    private Long rptDistNo;

    @Column(name = "part_1_2")
    private Long part12;

    @Column(name = "crm_cd")
    private Long crmCd;

    @Column(name = "crm_cd_desc")
    private String crmCdDesc;

    @Column(name = "mocodes")
    private String mocodes;

    @Column(name = "vict_age")
    private Long victAge;

    @Column(name = "vict_sex")
    private String victSex;

    @Column(name = "vict_descent")
    private String victDescent;

    @Column(name = "premis_cd")
    private Long premisCd;

    @Column(name = "premis_desc")
    private String premisDesc;

    @Column(name = "weapon_used_cd")
    private Long weaponUsedCd;

    @Column(name = "weapon_desc")
    private String weaponDesc;

    @Column(name = "status")
    private String status;

    @Column(name = "status_desc")
    private String statusDesc;

    @Column(name = "crm_cd_1")
    private Long crmCd1;

    @Column(name = "crm_cd_2")
    private Long crmCd2;

    @Column(name = "crm_cd_3")
    private String crmCd3;

    @Column(name = "crm_cd_4")
    private String crmCd4;

    @Column(name = "location")
    private String location;

    @Column(name = "cross_street")
    private String crossStreet;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lon")
    private String lon;

}
