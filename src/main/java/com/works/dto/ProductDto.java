package com.works.dto;

import com.works.config.Config;
import com.works.entities.Customer;
import com.works.entities.Product;
import com.works.repositories.ProductRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductDto {

    final ProductRepository pRepo ;

    public ProductDto(ProductRepository pRepo) {
        this.pRepo = pRepo;
    }


    // productList - start
    public Map<ERest,Object> productList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            List<Product> pageList = pRepo.findByOrderByProidAsc(pageable);
            Long totalcount = pRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Ürün Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Ürün Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // productList - end

    // productAdd - start
    public Map<ERest,Object> productAdd( Product product){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            Product pro = pRepo.save(product);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Ürün Ekleme başarılı");
            hm.put(ERest.result, pro );
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, "Bu bilgiler (" + product.getProductcode() + ","+product.getProductbarcode()+ ") ile daha önce kayıt yapılmış");
            }
        }
        return hm;
    }
    // productAdd - end


    // updateProduct - start
    public Map<ERest, Object> updateProduct( Product product) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if( product.getProid() != null ){

            Optional<Product> oProduct = pRepo.findById( product.getProid());
            if( oProduct.isPresent() ) {

                try {
                    pRepo.saveAndFlush( product );
                    hm.put(ERest.status, true);
                    hm.put(ERest.message, "Ürün Güncelleme başarılı");
                    hm.put(ERest.result, product);
                }catch (Exception ex){
                    hm.put(ERest.status, false);
                    if (ex.toString().contains("constraint")) {
                        hm.put(ERest.message, "Bu bilgiler (" + product.getProductcode() + ","+product.getProductbarcode()+ ") ile daha önce kayıt yapılmış");
                    }
                }

            }else {
                hm.put(ERest.status, false);
                hm.put(ERest.message, "Ürün Güncelleme işlemi sırasında hata oluştu!");
                hm.put(ERest.result, product);
            }
        }else {
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Ürün Güncelleme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, product);
        }


        return hm;

    }
    // updateProduct - end

    // deleteProduct - start
    public Map<ERest, Object> deleteProduct( String stProid ) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            int proid = Integer.parseInt(stProid);
            pRepo.deleteById(proid);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Ürün Silme başarılı");
            hm.put(ERest.result, stProid);
        }catch (Exception ex){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Ürün Silme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, stProid);
        }
        return hm;
    }
    // deleteProduct - end
}
