package com.zcy.cn.service.impl;

import com.zcy.cn.bean.BodyArgs;
import com.zcy.cn.bean.Custom;
import com.zcy.cn.bean.Users;
import com.zcy.cn.dao.CustomDao;
import com.zcy.cn.dto.CustomDTO;
import com.zcy.cn.enums.BodyEnum;
import com.zcy.cn.service.BodyArgsService;
import com.zcy.cn.service.CustomService;
import com.zcy.cn.service.UserService;
import com.zcy.cn.vo.AnnotationUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import sun.rmi.transport.ObjectTable;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CustomServiceImpl implements CustomService {

    @Autowired
    private CustomDao customDao;
    @Autowired
    private BodyArgsService bodyArgsService;

    @Override
    public Custom save(Custom custom) {
        return customDao.save(custom);
    }

    @Override
    public List<Custom> queryAll() {
        return customDao.findAll();
    }

    @Override
    public List<Custom> saveOrUpdateBatch(List<Custom> customs) {
        return customDao.saveAll(customs);
    }

    @Override
    public List<Map<String, Object>> buildCustomByBmi(AnnotationUser annotationUser) {
        List<Map<String, Object>> result = new ArrayList<>();
        if (annotationUser.getUId() == null) {
            return new ArrayList<>();
        }

        BodyArgs bodyArgs = bodyArgsService.findLastByUId(annotationUser.getUId());
        if (bodyArgs == null) {
            return new ArrayList<>();
        }

        Optional<BodyEnum> bodyEnum = Arrays.stream(BodyEnum.values()).filter(var -> var.getName().equals(bodyArgs.getBodyStatus())).findFirst();
        if (!bodyEnum.isPresent()) {
            return new ArrayList<>();
        }

        List<CustomDTO> resultOriginalFromData = customDao.findByCustomByBMI(bodyEnum.get().getIndex());
        Map<String, List<CustomDTO>> groupBySUperName = resultOriginalFromData.stream().collect(Collectors.groupingBy(CustomDTO::getName));
        groupBySUperName.forEach((k, v) -> {
            Map<String, Object> currBigMap = new HashMap<>();
            currBigMap.put("tigName", k);
            currBigMap.put("ins", v.get(0).getInstructions());
            currBigMap.put("cWeight", v.get(0).getCWeight());

            Map<String, List<CustomDTO>> currGroup = v.stream().collect(Collectors.groupingBy(CustomDTO::getSmallName));
            List<Map<String, Object>> currSmallList = new ArrayList<>();
            currGroup.forEach((k2, v2) -> {
                Map<String, Object> currSmallMap = new HashMap<>();
                List<String> dishNames = new ArrayList<>();
                currSmallMap.put("smallName", k2);
                currSmallMap.put("icon", v2.get(0).icon);
                v2.forEach(var -> {
                    if (!StringUtils.isEmpty(var.getDishBigName())) {
                        dishNames.add(var.getDishBigName()+"、 ");
                    }
                });
                currSmallMap.put("dishes", dishNames);
                currSmallList.add(currSmallMap);
            });
            currBigMap.put("small", currSmallList);
            result.add(currBigMap);
        });

        return result;
    }
}
