package com.sac.project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sac.project.domain.Finance;
import com.sac.project.domain.User;
import com.sac.project.dto.FinanceDto;
import com.sac.project.dto.FinanceUserDto;
import com.sac.project.exception.FinanceNotFoundException;
import com.sac.project.exception.UserNotFoundException;
import com.sac.project.repository.FinanceRepository;
import com.sac.project.repository.UserRepository;
import com.sac.project.util.FinanceMapper;
import com.sac.project.util.FinanceType;

import lombok.AllArgsConstructor;

@Transactional
@AllArgsConstructor
@Service
public class FinanceServiceImpl implements FinanceService {

    private final FinanceRepository repository;
    private final UserRepository userRepository;
    private final FinanceMapper mapper;

    @Override
    public Integer createNewFinance(FinanceDto dto) {
        repository.save(mapper.toDomain(dto));
        return 1;
    }

    @Override
    public List<FinanceDto> all() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Integer deleteFinance(Long id) throws FinanceNotFoundException {
        repository.deleteById(id);
        return 1;
    }

    @Override
    public FinanceDto fetchFinanceDetails(Long id) throws FinanceNotFoundException {
        Optional<Finance> op = repository.findById(id);
        return mapper.toDto(op.orElseThrow(() -> new FinanceNotFoundException("Finance " + id + " Not Found")));
    }

    @Override
    public Integer updateFinance(FinanceDto finance) {
        repository.save(mapper.toDomain(finance));
        return 1;
    }

    @Override
    public Integer createNewFinance(FinanceUserDto dto) {

        User user = userRepository.findById(dto.getUserId())
                                        .orElseThrow(() -> new UserNotFoundException("User Not Found"));

        Finance finance = new Finance();
        finance.setAmount(dto.getAmt());
        finance.setFinanceType(dto.getFinanceType());
        finance.setId(dto.getUserId());
        finance.setTag(dto.getTag());
        finance.setProcessedDate(dto.getInvDt());
        finance.setUser(user);

        repository.save(finance);

        return 1;
    }

    @Override
    public Double getTotalIncome() {
        return repository.getTotalAmountByType(FinanceType.INCOME);
    }
    @Override
    public Double getTotalExpenses(){
        return repository.getTotalAmountByType(FinanceType.EXPENSES);
    }

    @Override
    public List<User> allUserFinances(Long id) {
        return repository.findAllUserById(id);
    }

}