package com.zcy.cn.service.impl;

import com.zcy.cn.bean.DishBig;
import com.zcy.cn.dao.DishBigDao;
import com.zcy.cn.service.BigDishService;
import com.zcy.cn.vo.DishBigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BigDishServiceImpl implements BigDishService {

    @Autowired
    private DishBigDao dishBigDao;

    @Override
    public DishBigVO save(DishBigVO dishBigVo) {
        DishBig dishBig = dishBigVo.createDisBig();
        dishBig = dishBigDao.save(dishBig);
        BeanUtils.copyProperties(dishBig, dishBigVo, "dbId");
        return dishBigVo;
    }

    @Override
    public List<DishBig> queryAll() {
        return dishBigDao.findAll();
    }

    /**
     * DishBig一定为原全信息否则空字段数据库中将置null
     *
     * @param dishBig
     * @return
     */
    @Override
    public DishBig update(DishBig dishBig) {
        return dishBigDao.save(dishBig);
    }

    @Override
    public void delete(DishBig dishBig) {
        dishBigDao.delete(dishBig);
    }


}
