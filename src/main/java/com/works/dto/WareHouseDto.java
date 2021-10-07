package com.works.dto;


import com.works.config.Config;
import com.works.entities.WareHouse;
import com.works.repositories.WareHouseRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WareHouseDto {
    final WareHouseRepository wRepo;

    public WareHouseDto(WareHouseRepository wRepo) {
        this.wRepo = wRepo;
    }

    // warehouseList - start
    public Map<ERest,Object> warehouseList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            List<WareHouse> pageList = wRepo.findByOrderByWidAsc(pageable);
            Long totalcount = wRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Depo Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Depo Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // warehouseList - end

    // warehouseAdd - start
    public Map<ERest,Object> warehouseAdd( WareHouse wareHouse){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            WareHouse wHouse = wRepo.save(wareHouse);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Depo Ekleme başarılı");
            hm.put(ERest.result, wHouse );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, "Bu depo ismi (" + wareHouse.getWname() + ") ile daha önce kayıt yapılmış");
            }
        }
        return hm;
    }
    // warehouseAdd - end

    // updateWareHouse - start
    public Map<ERest, Object> updateWareHouse( WareHouse wareHouse) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if( wareHouse.getWid() != null ){

            Optional<WareHouse> oWareHouse = wRepo.findById( wareHouse.getWid());
            if( oWareHouse.isPresent() ) {

                try {
                    wRepo.saveAndFlush( wareHouse );
                    hm.put(ERest.status, true);
                    hm.put(ERest.message, "Depo Güncelleme başarılı");
                    hm.put(ERest.result, wareHouse);
                }catch (Exception ex){
                    hm.put(ERest.status, false);
                    if (ex.toString().contains("constraint")) {
                        hm.put(ERest.message, "Bu depo ismi (" + wareHouse.getWname() + ") ile daha önce kayıt yapılmış");
                    }
                }

            }else {
                hm.put(ERest.status, false);
                hm.put(ERest.message, "Depo Güncelleme işlemi sırasında hata oluştu!");
                hm.put(ERest.result, wareHouse);
            }
        }else {
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Depo Güncelleme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, wareHouse);
        }


        return hm;

    }
    // updateWareHouse - end


    // deleteWareHouse - start
    public Map<ERest, Object> deleteWareHouse( String stWid ) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            int wid = Integer.parseInt(stWid);
            wRepo.deleteById(wid);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Depo Silme başarılı");
            hm.put(ERest.result, stWid);
        }catch (Exception ex){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Depo Silme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, stWid);
        }
        return hm;
    }
    // deleteWareHouse - end
}
