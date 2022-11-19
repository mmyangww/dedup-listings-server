package com.clark.deduplistings.service.impl;

import com.clark.deduplistings.dao.TableADao;
import com.clark.deduplistings.domain.TableA;
import com.clark.deduplistings.service.TableAService;
import com.clark.deduplistings.vo.TableVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableAServiceImpl implements TableAService {

    @Autowired
    private TableADao tableADao;

    @Override
    public List<TableVO> findAll() {
        ArrayList<TableVO> tableAItems = new ArrayList<>();
        List<TableA> items = tableADao.findAll();

        for(TableA i: items){
            TableVO tableAVO = new TableVO();
            BeanUtils.copyProperties(i, tableAVO);
            tableAItems.add(tableAVO);
        }
        return tableAItems;
    }
}
