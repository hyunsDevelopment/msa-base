package com.foresys.app1.sample.service;

import com.foresys.app1.sample.mapper.SampleMapper;
import com.foresys.app1.sample.model.SamplePagingDTO;
import com.foresys.core.component.jwt.JwtComponent;
import com.foresys.core.model.jwt.TokenUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SampleService {

    private final SampleMapper sampleMapper;

    private final StringEncryptor encryptor;

    private final JwtComponent jwtComponent;

    /**
     * jasypt encoding example
     * @param params
     * @return
     */
    public String jasyptEncoding(String params) {
        return encryptor.encrypt(params);
    }

    /**
     * jasypt decoding example
     * @param params
     * @return
     */
    public String jasyptDecoding(String params) {
        return encryptor.decrypt(params);
    }

    /**
     * jwt create token example
     * @param user
     * @return
     */
    public String createToken(TokenUser user) {
        return jwtComponent.createToken(user);
    }

    /**
     * jwt decoding example
     * @param token
     * @return
     */
    public TokenUser jwtDecoding(String token) {
        return jwtComponent.decode(token);
    }

    /**
     * myBatis select query example
     * @return
     */
    public List<Map<String, Object>> select() {
        return sampleMapper.getMemberList();
    }

    /**
     * myBatis select query and paging example
     * @param params
     * @return
     */
    public PageInfo<Map<String, Object>> paging(SamplePagingDTO params) {
        PageHelper.startPage(params.getPageNum(), params.getPageSize(), params.getOrderBy());
        List<Map<String, Object>> list = sampleMapper.getMemberList();
        return new PageInfo<>(list, params.getNavigatePages());
    }
}
