package com.works.restcontrollers;

import com.works.dto.SupplierDto;
import com.works.entities.Suppliers;
import com.works.entities.WareHouse;
import com.works.utils.ERest;
import com.works.utils.Util;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/supplier")
public class SupplierRestController {

    final SupplierDto sDto;
    final Util util ;


    public SupplierRestController(SupplierDto sDto, Util util) {
        this.sDto = sDto;
        this.util = util;
    }

    @GetMapping("/list/{pageNo}")
    public Map<ERest, Object> list(@PathVariable String pageNo){
        return  sDto.supplierList(pageNo);
    }

    @PostMapping("/add")
    public Map<ERest, Object> add(@RequestBody @Valid Suppliers supplier, BindingResult bindingResult) {
        Map<ERest, Object> hm = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult));
            return hm;
        } else {
            return sDto.supplierAdd(supplier);
        }
    }

    @PutMapping("/update")
    public Map<ERest, Object> update(@RequestBody @Valid Suppliers supplier, BindingResult bindingResult){

        Map<ERest, Object> hm = new LinkedHashMap<>();
        if (bindingResult.hasErrors()) {
            hm.put(ERest.status, false);
            hm.put(ERest.errors, util.errors(bindingResult));
            return hm;
        } else {
            return  sDto.updateSupplier(supplier);
        }
    }

    @DeleteMapping("/delete/{sid}")
    public Map<ERest, Object> delete( @PathVariable String sid){
        return sDto.deleteSupplier(sid);
    }
}
