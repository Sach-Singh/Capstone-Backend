package com.sac.project.controller;


import java.util.List;

import javax.persistence.Id;
import javax.swing.text.html.HTML.Tag;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sac.project.domain.User;
import com.sac.project.dto.FinanceDto;
import com.sac.project.dto.FinanceUserDto;
import com.sac.project.repository.FinanceRepository;
import com.sac.project.service.FinanceService;
import com.sac.project.service.UserService;
import com.sac.project.util.AppResponse;
import com.sac.project.util.FinanceType;

import lombok.AllArgsConstructor;

@CrossOrigin
@AllArgsConstructor
@RequestMapping(value = "/finance")
@RestController
public class FinanceController {

    private final FinanceService service;
    private final FinanceRepository repository;

    
    @CrossOrigin
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> createNewFinance(@Valid @RequestBody FinanceDto dto) {

        final Integer sts = service.createNewFinance(dto);

        final AppResponse<Integer> response = AppResponse.<Integer>builder()
                                                    .sts("success")
                                                    .msg("finance added Successfully")
                                                    .bd(sts)
                                                    .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @CrossOrigin
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> createNewCustomerInvoice(@Valid @RequestBody FinanceUserDto dto) {

        final Integer sts = service.createNewFinance(dto);

        final AppResponse<Integer> response = AppResponse.<Integer>builder()
                                                    .sts("success")
                                                    .msg("Finance added Successfully")
                                                    .bd(sts)
                                                    .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @CrossOrigin
    @GetMapping(value = "/user-finances", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<List<FinanceDto>>> allFinances() {
        List<FinanceDto> finances = service.all();

        AppResponse<List<FinanceDto>> response = AppResponse.<List<FinanceDto>>builder()
                                                            .sts("success")
                                                            .msg("finances")
                                                            .bd(finances)
                                                            .build();

        return ResponseEntity.ok().body(response);
    }


    @CrossOrigin
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> deleteInvoice(@PathVariable Long id) {

        final Integer sts = service.deleteFinance(id);

        final AppResponse<Integer> response = AppResponse.<Integer>builder()
            .sts("success")
            .msg("Finance Deleted Successfully")
            .bd(sts)
            .build();

        return ResponseEntity.status(200).body(response);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<AppResponse<FinanceDto>> getFinanceById(@PathVariable Long id) {

        final FinanceDto dto = service.fetchFinanceDetails(id);

        final AppResponse<FinanceDto> response = AppResponse.<FinanceDto>builder()
                                                        .sts("success")
                                                        .msg("FInancial Details")
                                                        .bd(dto)
                                                        .build();
        return ResponseEntity.ok().body(response);
    }
    
    
    @CrossOrigin
    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<List<FinanceDto>>> allCustomerFinances(@PathVariable Long id) {
        List<FinanceDto> finances = service.allUserFInances(id);

        AppResponse<List<FinanceDto>> response = AppResponse.<List<FinanceDto>>builder()
                                                            .sts("success")
                                                            .msg("Users FInacne")
                                                            .bd(finances)
                                                            .build();

        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/total-income")
    public Double getTotalIncome() {
        return service.getTotalIncome();
    }

    @GetMapping("/total-expenses")
    public Double getTotalExpenses() {
        return service.getTotalExpenses();
    }



    @CrossOrigin
    @PutMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppResponse<Integer>> updateNewFinance(@Valid @RequestBody FinanceDto dto) {

        final Integer sts = service.updateFinance(dto);

        final AppResponse<Integer> response = AppResponse.<Integer>builder()
                                                    .sts("success")
                                                    .msg("Finance Updated Successfully")
                                                    .bd(sts)
                                                    .build();

        return ResponseEntity.ok().body(response);
    }
}
