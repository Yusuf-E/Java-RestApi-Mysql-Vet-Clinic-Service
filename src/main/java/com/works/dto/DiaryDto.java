package com.works.dto;


import com.works.config.Config;
import com.works.entities.Diary;
import com.works.model.DiarySession;
import com.works.repositories.DiaryRepository;
import com.works.repositories.redisrepositories.DiarySessionRepository;
import com.works.utils.ERest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DiaryDto {

    final DiaryRepository dRepo;
    final DiarySessionRepository dsRepo ;
    public DiaryDto(DiaryRepository dRepo, DiarySessionRepository dsRepo) {
        this.dRepo = dRepo;
        this.dsRepo = dsRepo;
    }

    // diaryList - start
    public Map<ERest,Object> diaryList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            List<Diary> pageList = dRepo.findByOrderByDidAsc(pageable);
            Long totalcount = dRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Not Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Not Listeleme işlemi sırasında hata oluştu!");
        }
        return hm;
    }
    // diaryList - end

    // diaryAdd - start
    public Map<ERest,Object> diaryAdd( Diary diary){
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            Diary dia = dRepo.save(diary);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Not Ekleme başarılı");
            hm.put(ERest.result, dia );
            DiarySession ds = new DiarySession();
            ds.setDid( UUID.randomUUID().toString() );
            ds.setTitle(dia.getTitle());
            ds.setDetail(dia.getDetail());
            ds.setDate(dia.getDate());
            ds.setDtime(dia.getDtime());
            dsRepo.save(ds);
        }catch (Exception ex) {
            hm.put(ERest.status, false);
            if (ex.toString().contains("constraint")) {
                hm.put(ERest.message, "Bu  Bilgiler ile daha önce kayıt yapılmış");
            }
        }
        return hm;
    }
    // diaryAdd - end

    // updateDiary - start
    public Map<ERest, Object> updateDiary( Diary diary) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if( diary.getDid() != null ){

            Optional<Diary> oDiary = dRepo.findById( diary.getDid());
            if( oDiary.isPresent() ) {

                try {
                    dRepo.save( diary );
                    hm.put(ERest.status, true);
                    hm.put(ERest.message, "Not Güncelleme başarılı");
                    hm.put(ERest.result, diary);
                }catch (Exception ex){
                    hm.put(ERest.status, false);
                    if (ex.toString().contains("constraint")) {
                        hm.put(ERest.message, "Bu bilgiler ile daha önce kayıt yapılmış");
                    }
                }

            }else {
                hm.put(ERest.status, false);
                hm.put(ERest.message, "Not Güncelleme işlemi sırasında hata oluştu!");
                hm.put(ERest.result, diary);
            }
        }else {
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Not Güncelleme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, diary);
        }


        return hm;

    }
    // updateDiary - end

    // deleteDiary - start
    public Map<ERest, Object> deleteDiary( String stDid ) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        try {
            int did = Integer.parseInt(stDid);
            dRepo.deleteById(did);
            hm.put(ERest.status, true);
            hm.put(ERest.message, "Not Silme başarılı");
            hm.put(ERest.result, stDid);
        }catch (Exception ex){
            hm.put(ERest.status, false);
            hm.put(ERest.message, "Not Silme işlemi sırasında hata oluştu!");
            hm.put(ERest.result, stDid);
        }
        return hm;
    }
    // deleteDiary - end

    // diaryRedisList - start
    public Map<ERest,Object> diaryRedisList(String pageNumber){
        Map<ERest,Object> hm = new LinkedHashMap<>();
        try {
            int ipageNumber = Integer.parseInt(pageNumber);
            Pageable pageable = PageRequest.of(ipageNumber, Config.pageSize);
            Iterable<DiarySession> pageList = dsRepo.findAll();
            Long totalcount = dRepo.count();
            hm.put(ERest.status,true);
            hm.put(ERest.message, "Not Listeleme işlemi başarılı");
            hm.put(ERest.totalSize,totalcount);
            hm.put(ERest.result, pageList);
            hm.put(ERest.pageStatus, (Config.pageSize * ipageNumber) + " - " + Config.pageSize);
        }catch (Exception ex){
            hm.put(ERest.status,false);
            hm.put(ERest.message,"Not Listeleme işlemi sırasında hata oluştu!"+ex);
        }
        return hm;
    }
    // diaryRedisList - end

}
