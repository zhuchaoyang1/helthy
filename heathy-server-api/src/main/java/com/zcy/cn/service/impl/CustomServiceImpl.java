package com.zcy.cn.service.impl;

import com.zcy.cn.bean.BodyArgs;
import com.zcy.cn.bean.Custom;
import com.zcy.cn.bean.Img;
import com.zcy.cn.dao.CustomDao;
import com.zcy.cn.dto.AdminCustomChangeDTO;
import com.zcy.cn.dto.CustomDTO;
import com.zcy.cn.enums.BodyEnum;
import com.zcy.cn.enums.ImgEnum;
import com.zcy.cn.service.BodyArgsService;
import com.zcy.cn.service.CustomService;
import com.zcy.cn.service.ImgService;
import com.zcy.cn.vo.AnnotationUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
    @Autowired
    private ImgService imgService;

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
    public Map<String, Object> buildCustomByBmi(AnnotationUser annotationUser) {
        Map<String, Object> mapResult = new HashMap<>();    // 结果集
        List<Map<String, Object>> result = new ArrayList<>();
        if (annotationUser.getUId() == null) {
            return new HashMap<>();
        }

        // 获取小程序无数据显示图片链接
        Img img = imgService.findByIndexs(ImgEnum.WECHATNODATAIMG.getIndexs()).get(0);
        mapResult.put("noData", img == null ? "" : img.getLocaltionPath());
        BodyArgs bodyArgs = bodyArgsService.findLastByUId(annotationUser.getUId());
        if (bodyArgs == null) {
            return mapResult;
        }

        Optional<BodyEnum> bodyEnum = Arrays.stream(BodyEnum.values()).filter(var -> var.getName().equals(bodyArgs.getBodyStatus())).findFirst();
        if (!bodyEnum.isPresent()) {
            return mapResult;
        }

        List<CustomDTO> resultOriginalFromData = customDao.findByCustomByBMI(bodyEnum.get().getIndex());
        Map<String, List<CustomDTO>> groupBySUperName = resultOriginalFromData.stream().collect(Collectors.groupingBy(CustomDTO::getName, LinkedHashMap::new, Collectors.toList()));
        groupBySUperName.forEach((k, v) -> {
            Map<String, Object> currBigMap = new HashMap<>();
            currBigMap.put("tigName", k);
            currBigMap.put("ins", v.get(0).getInstructions());
            currBigMap.put("cWeight", v.get(0).getCWeight());
            currBigMap.put("superId", v.get(0).getSId());

            Map<String, List<CustomDTO>> currGroup = v.stream().collect(Collectors.groupingBy(CustomDTO::getSmallName, LinkedHashMap::new, Collectors.toList()));
            List<Map<String, Object>> currSmallList = new ArrayList<>();
            currGroup.forEach((k2, v2) -> {
                Map<String, Object> currSmallMap = new HashMap<>();
                LinkedList<String> dishNames = new LinkedList<>();
                currSmallMap.put("smallName", k2);
                currSmallMap.put("icon", v2.get(0).icon);
                v2.forEach(var -> {
                    if (!StringUtils.isEmpty(var.getDishBigName())) {
                        dishNames.add(var.getDishBigName() + "、 ");
                    }
                });
                if (!CollectionUtils.isEmpty(dishNames)) {
                    String lastName = dishNames.getLast().substring(0, dishNames.getLast().length() - 2);
                    dishNames.removeLast();
                    dishNames.add(lastName);
                    currSmallMap.put("dishes", dishNames);
                }

                currSmallList.add(currSmallMap);
            });
            currBigMap.put("small", currSmallList);
            result.add(currBigMap);
        });

        mapResult.put("data", result);
        // 个人身材
        mapResult.put("bodyTest", bodyEnum.get().getName());
        return mapResult;
    }

    @Override
    public List<AdminCustomChangeDTO> adminBmiWeights(Integer bmiFlag) {
        return customDao.adminBmiWeight(bmiFlag);
    }

    @Override
    public void update(Custom custom) {
        customDao.update(custom.getCWeight(), custom.getSId(), custom.getBmiFlag());
    }
}
