package com.clark.deduplistings.service.impl;

import com.clark.deduplistings.dao.TableBDao;
import com.clark.deduplistings.domain.TableB;
import com.clark.deduplistings.service.TableBService;
import com.clark.deduplistings.vo.TableVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableBServiceImpl implements TableBService {

    @Autowired
    private TableBDao tableBDao;

    @Override
    public List<TableVO> findAll() {
        ArrayList<TableVO> tableBItems = new ArrayList<>();
        List<TableB> items = tableBDao.findAll();

        for(TableB i: items){
            TableVO tableBVO = new TableVO();
            BeanUtils.copyProperties(i, tableBVO);
            tableBItems.add(tableBVO);
        }
        return tableBItems;
    }
}
