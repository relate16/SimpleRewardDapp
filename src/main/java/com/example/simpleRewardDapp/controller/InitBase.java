package com.example.simpleRewardDapp.controller;

import com.example.simpleRewardDapp.entity.Cart;
import com.example.simpleRewardDapp.entity.Item;
import com.example.simpleRewardDapp.entity.Member;
import com.example.simpleRewardDapp.entity.Partner;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitBase {

    private final InitBaseService initBaseService;

    @PostConstruct
    public void init() {
        initBaseService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitBaseService {

        private final EntityManager em;

        @Transactional
        public void init() {
            Member member = new Member("Lee", 100000, 0);
            em.persist(member);
            Cart cart = new Cart(member);
            em.persist(cart);

            Partner gs25 = new Partner("GS25");
            Partner cgv = new Partner("CGV");
            Partner homeplus = new Partner("Homeplus");
            em.persist(gs25);
            em.persist(cgv);
            em.persist(homeplus);

            Item 컵라면 = new Item("컵라면", 1200, 10, 50, gs25);
            Item 삼각김밥 = new Item("삼각김밥", 800, 20, 35, gs25);
            Item 화장지 = new Item("화장지", 8000, 5, 38, homeplus);
            Item 샴푸 = new Item("샴푸", 6200, 15, 27, homeplus);
            Item 영화관람표 = new Item("영화관람표", 8000, 45, 70, cgv);
            Item 팝콘 = new Item("팝콘", 4000, 50, 55, cgv);
            em.persist(컵라면);
            em.persist(삼각김밥);
            em.persist(화장지);
            em.persist(샴푸);
            em.persist(영화관람표);
            em.persist(팝콘);
        }

    }

}
