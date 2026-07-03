package com.william.tomcat.service;

import com.william.tomcat.entity.CrimeDataEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CrimeJdbcService implements CrimeService {

    private final JdbcTemplate jdbcTemplate;

    public CrimeJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public Page<CrimeDataEntity> getCrimes(int page, int size) {
        log.info("Buscando con JDBC");
        int offset = page * size;

        final String DATA_SQL = """
        SELECT *
        FROM crime_data_from_2020_to_2024
        ORDER BY dr_no
        LIMIT ? OFFSET ?
        """;

        final String COUNT_SQL = """
        SELECT COUNT(*)
        FROM crime_data_from_2020_to_2024
        """;

        List<CrimeDataEntity> crimes = jdbcTemplate.query(
                DATA_SQL,
                CRIME_DATA_ROW_MAPPER,
                size,
                offset
        );

        Long total = jdbcTemplate.queryForObject(
                COUNT_SQL,
                Long.class
        );

        return new PageImpl<>(
                crimes,
                PageRequest.of(page, size),
                total != null ? total : 0L
        );
    }

    @Override
    public long getCrimeCount() {
        String sql = "SELECT COUNT(*) FROM crime_data_from_2020_to_2024";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        return count != null ? count : 0L;
    }

    @Override
    public QueryMode getQueryMode() {
        return QueryMode.JDBC;
    }

    private static final RowMapper<CrimeDataEntity> CRIME_DATA_ROW_MAPPER = (rs, rowNum) ->
            CrimeDataEntity.builder()
                    .drNo(rs.getLong("dr_no"))
                    .dateRptd(rs.getString("date_rptd"))
                    .dateOcc(rs.getString("date_occ"))
                    .timeOcc(rs.getLong("time_occ"))
                    .area(rs.getLong("area"))
                    .areaName(rs.getString("area_name"))
                    .rptDistNo(rs.getLong("rpt_dist_no"))
                    .part12(rs.getLong("part_1_2"))
                    .crmCd(rs.getLong("crm_cd"))
                    .crmCdDesc(rs.getString("crm_cd_desc"))
                    .mocodes(rs.getString("mocodes"))
                    .victAge(rs.getLong("vict_age"))
                    .victSex(rs.getString("vict_sex"))
                    .victDescent(rs.getString("vict_descent"))
                    .premisCd(rs.getLong("premis_cd"))
                    .premisDesc(rs.getString("premis_desc"))
                    .weaponUsedCd(rs.getLong("weapon_used_cd"))
                    .weaponDesc(rs.getString("weapon_desc"))
                    .status(rs.getString("status"))
                    .statusDesc(rs.getString("status_desc"))
                    .crmCd1(rs.getLong("crm_cd_1"))
                    .crmCd2(rs.getLong("crm_cd_2"))
                    .crmCd3(rs.getString("crm_cd_3"))
                    .crmCd4(rs.getString("crm_cd_4"))
                    .location(rs.getString("location"))
                    .crossStreet(rs.getString("cross_street"))
                    .lat(rs.getString("lat"))
                    .lon(rs.getString("lon"))
                    .build();
}


