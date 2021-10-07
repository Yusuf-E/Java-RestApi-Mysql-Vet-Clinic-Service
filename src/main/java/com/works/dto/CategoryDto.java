package com.works.dto;

import com.works.config.Config;
import com.works.entities.Category;
import com.works.entities.WareHouse;
import com.works.repositories.CategoryRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoryDto {

    final CategoryRepository cRepo;


    public CategoryDto(CategoryRepository cRepo) {
        this.cRepo = cRepo;
    }

    // categoryList - start
    public Map<ERest,Object> categoryList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            List<Category> pageList = cRepo.findByOrderByCaidAsc(pageable);
            Long totalcount = cRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Kategori Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Kategori Listeleme sırasında hata oluştu!");
        }
        return hm;
    }
    // categoryList - end

    // categoryAdd - start
    public Map<ERest,Object> categoryAdd( Category category){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            Category cat = cRepo.save(category);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Kategori Ekleme başarılı");
            hm.put(ERest.result, cat );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, "Bu Kategori ismi (" + category.getCategoryname() + ") ile daha önce kayıt yapılmış");
            }
        }
        return hm;
    }
    // categoryAdd - end

    // updateCategory - start
    public Map<ERest, Object> updateCategory( Category category) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if( category.getCaid() != null ){

            Optional<Category> oCategory = cRepo.findById( category.getCaid());
            if( oCategory.isPresent() ) {

                try {
                    cRepo.saveAndFlush( category );
                    hm.put(ERest.status, true);
                    hm.put(ERest.message, "Kategori Güncelleme başarılı");
                    hm.put(ERest.result, category);
                }catch (Exception ex){
                    hm.put(ERest.status, false);
                    if (ex.toString().contains("constraint")) {
                        hm.put(ERest.message, "Bu Kategori ismi (" + category.getCategoryname() + ") ile daha önce kayıt yapılmış");
                    }
                }

            }else {
                hm.put(ERest.status, false);
                hm.put(ERest.message, "Kategori Güncelleme işlemi sırasında hata oluştu!");
                hm.put(ERest.result, category);
            }
        }else {
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Kategori Güncelleme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, category);
        }


        return hm;

    }
    // updateCategory - end

    // deleteCategory - start
    public Map<ERest, Object> deleteCategory( String stCid ) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            int wid = Integer.parseInt(stCid);
            cRepo.deleteById(wid);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Kategori Silme başarılı");
            hm.put(ERest.result, stCid);
        }catch (Exception ex){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Kategori Silme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, stCid);
        }
        return hm;
    }
    // deleteCategory - end
}
