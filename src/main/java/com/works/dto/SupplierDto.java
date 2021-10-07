package com.works.dto;

import com.works.config.Config;
import com.works.entities.Suppliers;
import com.works.repositories.SupplierRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierDto {

    final SupplierRepository sRepo;

    public SupplierDto(SupplierRepository sRepo) {
        this.sRepo = sRepo;
    }

    // supplierList - start
    public Map<ERest,Object> supplierList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            List<Suppliers> pageList = sRepo.findByOrderBySidAsc(pageable);
            Long totalcount = sRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Tedarikçi Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Tedarikçi Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // supplierList - end

    // supplierAdd - start
    public Map<ERest,Object> supplierAdd( Suppliers supplier){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            Suppliers supp = sRepo.save(supplier);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Tedarikçi Ekleme başarılı");
            hm.put(ERest.result, supp );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, "Bu Tedarikçi Bilgileri ile daha önce kayıt yapılmış");
            }
        }
        return hm;
    }
    // supplierAdd - end

    // updateSupplier - start
    public Map<ERest, Object> updateSupplier( Suppliers supplier) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if( supplier.getSid() != null ){

            Optional<Suppliers> oSupplier = sRepo.findById( supplier.getSid());
            if( oSupplier.isPresent() ) {

                try {
                    sRepo.saveAndFlush( supplier );
                    hm.put(ERest.status, true);
                    hm.put(ERest.message, "Tedarikçi Güncelleme başarılı");
                    hm.put(ERest.result, supplier);
                }catch (Exception ex){
                    hm.put(ERest.status, false);
                    if (ex.toString().contains("constraint")) {
                        hm.put(ERest.message, "Bu Tedarikçi Bilgileri ile daha önce kayıt yapılmış");
                    }
                }

            }else {
                hm.put(ERest.status, false);
                hm.put(ERest.message, "Tedarikçi Güncelleme işlemi sırasında hata oluştu!");
                hm.put(ERest.result, supplier);
            }
        }else {
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Tedarikçi Güncelleme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, supplier);
        }


        return hm;

    }
    // updateSupplier - end

    // deleteSupplier - start
    public Map<ERest, Object> deleteSupplier( String stSid ) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            int wid = Integer.parseInt(stSid);
            sRepo.deleteById(wid);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Tedarikçi Silme başarılı");
            hm.put(ERest.result, stSid);
        }catch (Exception ex){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Tedarikçi Silme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, stSid);
        }
        return hm;
    }
    // deleteSupplier - end
}
