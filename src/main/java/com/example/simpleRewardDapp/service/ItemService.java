package com.example.simpleRewardDapp.service;

import com.example.simpleRewardDapp.dto.ItemDto;
import com.example.simpleRewardDapp.entity.Item;
import com.example.simpleRewardDapp.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Item getItem(String itemName) {
        Optional<Item> findItem = itemRepository.findByName(itemName);
        return findItem.orElseThrow(() -> new RuntimeException("해당 아이템이 없습니다"));
    }
}
